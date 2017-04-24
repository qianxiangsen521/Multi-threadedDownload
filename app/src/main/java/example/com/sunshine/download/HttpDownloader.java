package example.com.sunshine.download;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

class HttpDownloader {
    private static final String TEMP_SUFFIX = ".tmp";

    final DatabaseHelper databaseHelper;
    final ExecutorService mThreadPool;
    /** 下载的速度 */
    private int downloadSpeed = 0;
    /** 下载用的时间 */
    private int usedTime = 0;
    /** 开始时间 */
    private long startTime;
    /** 当前时间 */
    private long curTime;
    /**
     * 临时下载量
     */
    private long mdownloadsize;

    final DownloadManagerListenerModerator downloadManagerListenerModerator;

    HttpDownloader(DatabaseHelper databaseHelper,ExecutorService mThreadPool,DownloadManagerListenerModerator downloadManagerListenerModerator){
        this.downloadManagerListenerModerator = downloadManagerListenerModerator;
        this.databaseHelper = databaseHelper;
        this.mThreadPool = mThreadPool;

    }
    void enqueue(Task request) {

        mThreadPool.submit(new Downloadrequest(request));
    }

    class Downloadrequest implements Runnable {

        private Task request;


        Downloadrequest(Task request) {
            this.request = request;
        }

        @Override
        public void run() {
                            int statusCode = -1;
            if (request.getState().equals(
                    request.STATUS_IDLE)) {
               statusCode = doDownload(request);

           }

        }
        public int doDownload(Task request) {
            // 不能设置成全局变量，会导致写文件出错
            HttpURLConnection conn = null;
            InputStream inStream = null;
            RandomAccessFile os = null;
            File destFile = null;
            int statusCode = 0;
            try {
                URL url = new URL(request.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Referer", request.getUrl());
                conn.setRequestProperty("Charset", "UTF-8");
                // 在conn.connect();之前new os 不然会导致暂停后无法重新下载
                destFile = setupFile(request.getSave_address() + TEMP_SUFFIX);
                // 如果下载文件存在
                if (destFile.exists()) {

                } else {
                    request.setDownloadSize(0);
                }
                if (destFile.length() > 0) {
                    conn.setRequestProperty(
                            "Range",
                            "bytes=" + request.getDownloadSize() + "-"
                                    + request.getSize());
                    Log.d("TAG", "request.getDownloadSize() -- "+request.getDownloadSize());
                    os = new RandomAccessFile(destFile,"rw");
                    os.seek(request.getDownloadSize());


                } else {
                    os = new RandomAccessFile(destFile,"rw");

                }
                conn.setRequestProperty(
                        "User-Agent",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("content-type", "text/html");
                conn.connect();
                if (conn.getResponseCode() == 200 || conn.getResponseCode() == 206) {

                    try {
                        inStream = conn.getInputStream();

                        long filesize = conn.getContentLength();
                        if (request.getDownloadSize() == 0) {
                            request.setSize(filesize);
                        }
                        byte buffer[] = new byte[800096];
                        int length = 0;
                        //开始下载
                        notifyStart(request);

                        startTime = System.currentTimeMillis();
                        while (request.getState().equals(
                                request.STATUS_START)
                                && (length = inStream
                                .read(buffer, 0, buffer.length)) != -1) {
                            os.write(buffer, 0, length);
                            int downloadSize =(int) request.getDownloadSize();
                            downloadSize += length;
                            Log.d("TAG", "downloadSize -- "+downloadSize);
                            request.setDownloadSize(downloadSize);

                            int mdownloadsize = (int) request.getMtemdownsize();// 获取临时下载数量
                            mdownloadsize += length;
                            request.setMtemdownsize(mdownloadsize);

                            Log.d("TAG", "mdownloadsize -- "+mdownloadsize);

                            curTime = System.currentTimeMillis();

                            usedTime = (int) ((curTime - startTime) / 1000);
                            if (usedTime == 0)
                                usedTime = 1;
                            downloadSpeed = (int) ((mdownloadsize / usedTime) / 1024);
                            Log.d("TAG", "downloadSpeed -- "+downloadSpeed);
                            // 获取到停止状态跳出循环
                            request.setSpeed(downloadSpeed);
                            notifyProgress(request);
                            if (request.getState().equals(
                                    request.STATUS_PAUSE)) {
                                break;
                            }
                        }
                        if (request.getSize() == request.getDownloadSize()
                                && !(request.getState()
                                .equals(request.STATUS_COMPLETE))) {
                            destFile.renameTo(new File(request.getSave_address()));
                            request.setMtemdownsize(0);
                            request.setState(request.STATUS_COMPLETE);
                            notifyComplete(request);
                            os.close();
                        } else {
                            notifyProgress(request);
                        }
                        os.close();
                    } catch (IOException e) {
                        os.close();
                        request.setState(request.STATUS_PAUSE);
                        notifyProgress(request);
                    }

                } else {
                    request.setMtemdownsize(0);
                    request.setState(request.STATUS_ERROR);
                    notifyError(request);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setMtemdownsize(0);
                request.setState(request.STATUS_ERROR);
                notifyError(request);

            }

            finally {
                try {
                    inStream.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return statusCode;
        }
    }

    private File setupFile(String destUri) {
        File outFile = new File(destUri);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        return outFile;
    }

    synchronized private void notifyStart(Task request) {
        //开始状态
        request.setState(request.STATUS_START);
        //更新数据库
        databaseHelper.update(request);
        //刷新UI
        downloadManagerListenerModerator.OnDownloadRebuildStart(request);
    }

    synchronized private void notifyComplete(Task request) {
        request.setState(request.STATUS_COMPLETE);
        //更新数据库
        databaseHelper.update(request);

        downloadManagerListenerModerator.OnDownloadFinished(request);
    }

    synchronized public void notifyProgress(Task request) {

        //更新数据库
        databaseHelper.update(request);

        downloadManagerListenerModerator.onDownloadProcess(request);
    }

    synchronized private void notifyError(Task request) {
        request.setState(request.STATUS_ERROR);
        //更新数据库
        databaseHelper.update(request);
        downloadManagerListenerModerator.OnDownloadErrered(request);
    }



}
