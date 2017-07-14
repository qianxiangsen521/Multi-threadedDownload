/*
 * Project: babylon
 * 
 * File Created at 2011-7-26
 * 
 * Copyright 2011 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */

package example.com.sunshine.download.request;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import example.com.sunshine.download.Application.TLiveApplication;

/**
 * 文件帮助类
 */

@SuppressLint("DefaultLocale")
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 应用根目录
     */
    public static final String BASE_DIR = "Android/data/example.com.sunshine/volley";
    /**
     * 系统照片存储路径
     */
    public static final String SYSTEM_CAMERA_DIR = "/DCIM/Camera";
    /**
     * 应用拍照照片存储目录
     */
    public static final String PHOTO_DIR = "/photo";
    /**
     * 网络缓存目录
     */
    public static final String CNR_CACHE_DIR = "/cache";
    /**
     * 图片下载路径
     */
    public static final String CNR_DOWNLOAD_DIR = "/download";
    /**
     * 临时路径
     */
    public static final String CNR_TEMP_DIR = "/temp";
    /**
     * 应用升级目录
     */
    public static final String UPGRADE_DIR = "/upgrade";

    /**
     * 判断外设是否挂载
     */
    public static boolean isExternalStorageMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断是否有externalCacheDir
     */
    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 判断sd卡是否可用(包括内部sd卡)
     */
    public static boolean isSDCardAvailable() {
        if (isExternalStorageMounted()) {
            return true;
        } else {
            if (getInternalSDCardDir().exists()) {
                return true;
            }
        }
        return false;
    }

    /****************************** 路径的获取 Start *************************/
    /**
     * 获取磁盘根目录
     *
     * @param context
     * @return
     */
    private static File getDiskRootDir(Context context) {
        String rootPath = isExternalStorageMounted() ? Environment
                .getExternalStorageDirectory().getPath()
                : context.getFilesDir().getAbsolutePath();
        return new File(rootPath);
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    private static File getInternalSDCardDir() {
        File internalSDCardDir = new File(Environment.getDataDirectory()
                .getAbsolutePath(), "sdcard");
        return internalSDCardDir;
    }

    /**
     * 获取应用根目录
     *
     * @param context
     * @return
     */
    public static File getBaseDir(Context context) {
        File basePath = new File(getDiskRootDir(context), BASE_DIR);
        if (!basePath.exists()) {
            if (!basePath.mkdirs()) {
                basePath = null;
            }
        }
        return basePath;
    }

    /**
     * 获取应用子目录
     *
     * @param context
     * @param dir
     * @return
     */
    public static File getSubDir(Context context, String dir) {
        if (!TextUtils.isEmpty(dir)) {
            File file = new File(getBaseDir(context), dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return getBaseDir(context);

    }

    /**
     * 获取系统相机存储路径
     */
    public static File getCameraDir(Context context) {
        File dir;
        if (isExternalStorageMounted()) {
            dir = new File(Environment
                    .getExternalStorageDirectory().getPath(), SYSTEM_CAMERA_DIR);
        } else {
            if (getInternalSDCardDir().exists()) {
                dir = new File(getInternalSDCardDir(), SYSTEM_CAMERA_DIR);
            } else {
                dir = new File(getDiskRootDir(context), SYSTEM_CAMERA_DIR);
            }
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取应用程序的Files目录 如:sdcard/Android/data/packageName/files
     */
    @SuppressLint("NewApi")
    public static File getFilesDir(Context context) {
        File filesDir = null;
        if (isExternalStorageMounted()) {
            if (hasExternalCacheDir()) {
                filesDir = context.getExternalFilesDir(null);
            } else {
                filesDir = new File(Environment.getExternalStorageDirectory(), "/Android/data/"
                        + context.getApplicationInfo().packageName + "/files/");
                filesDir.mkdirs();
            }
        } else {
            filesDir = context.getFilesDir();
        }
        if (filesDir != null && !filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }

    /**
     * Get a usable Cache directory(external if available, internal otherwise) 类似路径
     * /sdcard/Android/data/com.cnr.fm/cache
     */
    private static File getDiskCacheDir(Context context) {
        String cacheDir = isExternalStorageMounted() ?
                getExternalCacheDir(context).getPath() :
                context.getCacheDir().getPath();
        return new File(cacheDir);
    }

    /**
     * 获取外设应用的的缓存目录
     */
    private static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }
        // 在Froyo之前需要自己建立缓存目录
        String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);

    }
    /**
     * 获取不同类型Cache的路径
     *
     * @param context
     * @param type
     * @return
     */
    private static File getCacheDirByType(Context context, String type) {
        File cacheDir = getDiskCacheDir(context);
        File typeCacheDir = new File(cacheDir, type);
        if (typeCacheDir != null && !typeCacheDir.exists()) {
            typeCacheDir.mkdirs();
        }
        return typeCacheDir;
    }

    /**
     * 获取图片缓存的路径
     *
     * @param context
     * @return
     */
    public static File getImageCacheDir(Context context) {
        return getCacheDirByType(context, "uil-images");
    }


    /**
     * 获取本地资源绝对路径
     *
     * @param relativelyPath 资源的相对路径
     */
    public static String getResourceAbsolutePath(String relativelyPath) {
        return getBaseDir(TLiveApplication.getInstance()) + relativelyPath;
    }

    /****************************** 路径的获取 End *************************/

    /****************************** 创建目录 *************************/
    /**
     * 创建volley缓存目录
     */
    public static File createVolleyCacheDir(Context context) {
        return getSubDir(context, CNR_CACHE_DIR);
    }

    /**
     * 创建下载目录
     */
    public static File createDownloadDir(Context context) {
        return getSubDir(context, CNR_DOWNLOAD_DIR);
    }

    /**
     * 创建临时目录
     */
    public static File createTmpDir(String parentDir) {
        String newDir = CNR_TEMP_DIR;
        if (!TextUtils.isEmpty(parentDir)) {
            newDir = parentDir + File.separator
                    + CNR_TEMP_DIR;
        }
        return getSubDir(TLiveApplication.getInstance(), newDir);
    }


    /**************************** 文件的基本操作 *****************************/
    /**
     * 创建文件
     */
    public static File createFile(String parentFilePath, String fileName, String extension) throws IOException {
        File file = new File(parentFilePath + File.separator + fileName + extension);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                return null;
            }
        }
        return file;
    }

    /**
     * 生成目录路径
     */
    public static boolean makeDirs(File folder) {
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static boolean makDirs(String folder) {
        if (TextUtils.isEmpty(folder)) {
            return false;
        }
        return makeDirs(new File(folder));
    }

    /**
     * 判断文件是否已经存在
     *
     * @param file
     * @return
     */
    public static boolean isExist(String file) {
        if (TextUtils.isEmpty(file)) {
            return false;
        }
        return isExist(new File(file));
    }

    public static boolean isExist(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        System.out.println("dir:" + file.isDirectory());
        System.out.println("isExist:" + isExist(file));
        return !file.isDirectory() && isExist(file);
    }

    /**
     * 删除文件 删除目录下的全部文件和目录
     */
    public static void deleteAll(File path) {
        if (path == null)
            return;
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAll(files[i]);
        }
        path.delete();
    }

    public static void deleteAll(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        deleteAll(new File(path));
    }

    /**
     * 获取文件夹下的所有文件
     *
     * @param fileFilter 文件过滤器
     * @return
     */
    public static ArrayList<File> getAllFiles(String dirpath, FileFilter fileFilter) {
        ArrayList<File> files = null;
        if (TextUtils.isEmpty(dirpath)) {
            return files;
        }
        files = new ArrayList<File>();
        File dir = new File(dirpath);
        if (!isExist(dir)) {
            return files;
        }
        if (dir.isFile()) {
            files.add(dir);
            return files;
        }
        File[] lists = dir.listFiles();
        for (int i = 0; i < lists.length; i++) {
            File temp = lists[i];
            if (temp.isDirectory()) {
                ArrayList<File> tempFiles = getAllFiles(temp.getAbsolutePath(), fileFilter);
                if (tempFiles != null) {
                    files.addAll(tempFiles);
                }
            } else {
                if (fileFilter.filter(temp)) {
                    files.add(temp);
                }
            }
        }
        return files;
    }

    /**
     * 获取目录下的所有图片
     */
    public static ArrayList<File> getImageFilesSortbyName(String dirpath) {
        ArrayList<File> files = getAllFiles(dirpath, imageFileFilter);
        if (!StringTool.isListValidate(files)) {
            return null;
        }
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                try {
                    //默认比较数字
                    Integer lhsNum = Integer.valueOf(extractFileName(lhs.getAbsolutePath(), false));
                    Integer rhsNum = Integer.valueOf(extractFileName(rhs.getAbsolutePath(), false));
                    return lhsNum.compareTo(rhsNum);
                } catch (NumberFormatException e) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            }
        });
        return files;
    }

    /**
     * 查看是否是图片 以文件后缀名为依据
     */
    private static boolean checkIsImageFile(File f) {
        boolean isImageFile = false;
        // 获取扩展名
        String suffix = getFileNameSuffix(f).toLowerCase();
        isImageFile = suffix.equals("jpg")
                || suffix.equals("png")
                || suffix.equals("jpeg")
                || suffix.equals("bmp");
        return isImageFile;
    }

    /**
     * 判断是否是音频文件
     */
    public static boolean checkIsAudioFile(String file) {
        if (isFile(file)) {
            String suffix = getFileNameSuffix(new File(file)).toLowerCase();
            if ("mp3".equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提起文件名
     *
     * @param suffix 是否需要后缀名
     */
    public static String extractFileName(String path, boolean suffix) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        String filename = "";
        int dot = path.lastIndexOf("/");
        if ((dot > -1) && (dot < (path.length()))) {
            filename = path.substring(dot + 1);
        }
        if (!suffix && !TextUtils.isEmpty(filename)) {
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        return filename;
    }

    /**
     * 获得文件名后缀
     *
     * @param file
     * @return
     */
    public static String getFileNameSuffix(File file) {
        String suffix = "";
        if (file != null) {
            String filePath = file.getAbsolutePath();
            return getFileNameSuffix(filePath);
        }
        return suffix;
    }

    /**
     * 获得文件名后缀 不带.号
     *
     * @param filePath
     * @return
     */
    public static String getFileNameSuffix(String filePath) {
        String suffix = "";
        if (filePath != null) {
            if (!TextUtils.isEmpty(filePath)) {
                int dotIndex = filePath.lastIndexOf(".");
                if (dotIndex > 0) {
                    suffix = filePath.substring(dotIndex + 1);
                }
            }
        }
        return suffix;
    }

    /**
     * 获取除扩展名外的文件路径
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取除文件名外的文件路径
     */
    public static String getFilePath(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        int dot = url.lastIndexOf('/');
        if ((dot > -1) && (dot < (url.length()))) {
            return url.substring(0, dot);
        }
        return url;
    }

    /**
     * 获取该路径下的可用空间
     */
    @SuppressWarnings("deprecation")
    public static long getUsableSpace(String path) {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        // return new File(path).getUsableSpace();
        // }
        StatFs statfs = new StatFs(path);
        return (long) statfs.getBlockSize() * (long) statfs.getAvailableBlocks();
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return the length of this file in bytes
     */
    public static long getFileSize(File file) {
        if (!isExist(file)) {
            return 0;
        }
        long size = 0;
        if (file.isFile()) {
            size = file.length();
        } else {
            for (File f : file.listFiles()) {
                size += getFileSize(f);
            }
        }
        return size;
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(File sourceFile, File targetFile) {
        FileInputStream input = null;
        BufferedInputStream inBuff = null;

        FileOutputStream output = null;
        BufferedOutputStream outBuff = null;

        try {
            input = new FileInputStream(sourceFile);
            inBuff = new BufferedInputStream(input);
            output = new FileOutputStream(targetFile);
            outBuff = new BufferedOutputStream(output);
            byte[] b = new byte[1024 * 5];
            int len;
            int icount = 0;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
                icount++;
                if (icount % 5 == 0) {
                    outBuff.flush();
                }
            }
            outBuff.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            IOUtils.close(inBuff);
            IOUtils.close(outBuff);
            IOUtils.close(output);
            IOUtils.close(input);
        }
        return false;
    }

    /**
     * 将字符转附加到文件
     *
     * @param str
     * @param file
     * @param charset 字符编码
     */
    public static void appendStringToFile(String str, File file, String charset) {
        saveStringToFile(str, file, charset, true);
    }

    /**
     * 将字符转附加到文件
     *
     * @param str
     * @param file
     */
    public static void appendStringToFile(String str, File file) {
        saveStringToFile(str, file, HTTP.UTF_8, true);
    }

    /**
     * 将byte数组保存到文本
     *
     * @param bytes    文件内容
     * @param file     文件
     * @param isAppend 是否采用附加的方式
     */
    public static void saveBytesToFile(byte[] bytes, File file, boolean isAppend) {
        if (null == file) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && (!parentFile.exists())) {
            parentFile.mkdirs();
        }
        FileOutputStream outStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outStream = new FileOutputStream(file, isAppend);
            outStream.write(bytes);
            outStream.flush();
        } catch (FileNotFoundException e) {
            Log.w(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.w(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(outStream);
        }
    }

    /**
     * 将流保存到文件面
     *
     * @param is   输入流
     * @param file 目标文件
     */
    public static void saveInputStreamToFile(InputStream is, File file) {
        if (is == null) {
            return;
        }
        if (file == null) {
            return;
        }
        File parentFile = file.getParentFile();
        if (!isExist(parentFile)) {
            parentFile.mkdirs();
        }
        BufferedInputStream inBuff = null;
        FileOutputStream output = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(is);
            output = new FileOutputStream(file);
            outBuff = new BufferedOutputStream(output);
            byte[] b = new byte[1024 * 5];
            int len;
            int icount = 0;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
                icount++;
                if (icount % 5 == 0) {
                    outBuff.flush();
                }
            }
            outBuff.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outBuff != null) {
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inBuff != null) {
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 将字符串保存到文本
     *
     * @param str      字符内容
     * @param file     文件
     * @param charset  字符编码
     * @param isAppend 是否采用附加的方式
     */
    public static void saveStringToFile(String str, File file, String charset, boolean isAppend) {
        saveBytesToFile(EncodingUtils.getBytes(str, charset), file, isAppend);
    }

    /**
     * 保存图片
     *
     * @param bm 图片
     * @return 图片保存路径
     */
    public static String saveBitmapToFile(Bitmap bm, String path) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 下载图片到Download目录下
     *
     * @param path   源文件路径
     * @param suffix 源文件后缀名
     */
    public static boolean saveToDownloadDir(String path, String suffix) {
        if (!isExternalStorageMounted()) {
            return false;
        }

        File oriFile = new File(path);
        File targetFile = new File(createDownloadDir(TLiveApplication.getInstance()),
                extractFileName(path, false) + "." + suffix);

        if (copyFile(oriFile, targetFile)) {
            forceRefreshSystemAlbum(targetFile.getAbsolutePath());
            return true;
        }
        return false;
    }

    /**
     * 下载图片到/DCIM/Camera/目录下，并且往系统数据库插入
     */
    public static String downloadToAlbum(Context context, File file) {
        String newFileDir = Environment.getExternalStorageDirectory()
                .toString() + SYSTEM_CAMERA_DIR;
        File dirFile = new File(newFileDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File targetFile = new File(newFileDir
                + UUID.randomUUID().toString() + ".jpg");
        if (copyFile(file, targetFile)) {
            String filePath = targetFile.getAbsolutePath();
            insertIntoContent(context, filePath, "albumImageIv/gif");
            return filePath;
        }
        return "";
    }

    /**
     * 强制扫描图片，使其能够展示在相册
     */
    private static void forceRefreshSystemAlbum(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String type = options.outMimeType;
        MediaScannerConnection
                .scanFile(TLiveApplication.getInstance(), new String[]{
                        path
                }, new String[]{
                        type
                }, null);

    }

    /**
     * 将图片信息插入本地数据库
     */
    private static boolean insertIntoContent(Context context, String fileName, String mimeType) {
        try {
            if (TextUtils.isEmpty(mimeType)) {
                mimeType = "albumImageIv/jpeg";
            }
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("_data", fileName);
            localContentValues.put("description", "save albumImageIv ---");
            localContentValues.put("mime_type", mimeType);
            localContentValues.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

            ContentResolver localContentResolver = context.getContentResolver();
            Uri localUri = Images.Media.EXTERNAL_CONTENT_URI;
            localContentResolver.insert(localUri, localContentValues);
            return true;
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
        return false;
    }

    /**
     * 从uri中提取文件路径uri
     *
     * @param uri
     * @param context pass null if uri is not "content" schema
     * @return
     */
    public static Uri getFileUri(String uri, Context context) {
        String path = null;
        Uri pathUri = null;
        if (uri != null) {
            uri = Uri.decode(uri.toString());
            pathUri = Uri.parse(uri);
            // If there is no scheme, then it must be a file
            if (pathUri.getScheme() == null) {
                pathUri = Uri.fromFile(new File(uri));
            } else if ("file".equals(pathUri.getScheme())) {
                path = pathUri.getPath();
                // nothing to change
            } else if ("content".equals(pathUri.getScheme())) {
                // query path by uri
                ContentResolver resolver = context.getContentResolver();
                Cursor cursor = resolver.query(pathUri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex("_data"));
                    // data_path:/data/data/com.android.providers.downloads/cache/buddy-2.apk
                    pathUri = Uri.fromFile(new File(path));
                }
            }
        }
        return pathUri;
    }

    /**
     * 是否是网络文件
     *
     * @param file
     * @return
     */
    public static boolean isHttpFile(String file) {
        if (!TextUtils.isEmpty(file)) {
            if (file.startsWith(FileScheme.HTTP_SCHEME.toString()) ||
                    file.startsWith(FileScheme.HTTPS_SCHEME.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是本地文件
     *
     * @param file
     * @return
     */
    public static boolean isLocalFile(String file) {
        if (!TextUtils.isEmpty(file)) {
            if (file.startsWith(FileScheme.FILE_SCHEME.toString())) {
                file = file.replace(FileScheme.FILE_SCHEME.toString(), "");
            }
            if (isExist(file)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取本地图片路径，去掉"file://"
     */
    public static String getLocalFilePath(String file) {
        if (isLocalFile(file)) {
            file = file.replace(FileScheme.FILE_SCHEME.toString(), "");
            return file;
        }
        return "";
    }


    /**
     * 文件过滤器接口
     */
    public interface FileFilter {
        boolean filter(File file);
    }

    /**
     * 图片过滤器
     */
    static FileFilter imageFileFilter = new FileFilter() {
        @Override
        public boolean filter(File file) {
            return checkIsImageFile(file);
        }
    };


    /**
     * 文件Shceme
     */
    public enum FileScheme {
        FILE_SCHEME("file://"), HTTP_SCHEME("http://"), HTTPS_SCHEME("https://"), CONTENT_SCHEME(
                "content://");
        String scheme;

        FileScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        @Override
        public String toString() {
            return scheme;
        }
    }

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCacheDirDownload(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? getExternalCacheDirDownload(context).getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     * otherwise.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDirDownload(Context context) {
        if (hasFroyo()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/Download/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
}
