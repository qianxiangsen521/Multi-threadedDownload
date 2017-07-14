
package example.com.sunshine.download.request;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import java.io.File;

/**
 * @Description Volley 单例模式，提供网络请求队列，图片加载器
 * @Date 2016-3-19 下午5:28:06
 */
public class VolleySingleton {
    /**
     * 缓存http目录
     */
    public static final String VOLLEY_DIR_HTTP = "http";
    /**
     * 设置缓存Json文件的最大磁盘空间1M(单位bytes)。
     */
    private static final int MAX_DISK_CACHE_HTTP = 1024 * 1024;

    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 30 * 1000;// 超时时间 30s

    /**
     * The default number of retries 默认都不重试
     **/
    public static final int DEFAULT_MAX_RETRIES = 0;

    private static VolleySingleton mInstance;
    /**
     * http 请求队列
     */
    private RequestQueue mHttpRequestQueue;
    /**
     * Volley 重传策略
     */
    private RetryPolicy mCyRetryPolicy = new DefaultRetryPolicy(
            DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private Context mContext;

    private VolleySingleton(Context context) {
        this.mContext = context;
        mHttpRequestQueue = getHttpRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * 获取http请求队列
     */
    private RequestQueue getHttpRequestQueue() {
        if (mHttpRequestQueue == null) {
            mHttpRequestQueue = newRequestQueue(
                    mContext.getApplicationContext(),
                    new File(FileUtils.createVolleyCacheDir(mContext),
                            VOLLEY_DIR_HTTP), MAX_DISK_CACHE_HTTP);
        }
        return mHttpRequestQueue;
    }

    /**
     * 往队列添加请求
     */
    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, mCyRetryPolicy);
    }

    /**
     * 往队列添加请求
     *
     * @param retry 重试策略
     */
    private <T> void addToRequestQueue(Request<T> req, RetryPolicy retry) {
        req.setShouldCache(true);
        req.setRetryPolicy(retry);
        getHttpRequestQueue().add(req);
    }

    /**
     * Creates a unique cache key based on a url value
     *
     * @param url url to be used in key creation
     * @return cache key value
     */
    private String createKey(String url) {
        return String.valueOf(url.hashCode());
    }

    /**
     * 生成请求队列
     *
     * @param cacheFile           本地缓存文件路径
     * @param maxCacheSizeInBytes 本地缓存最大的存储Size
     */
    private RequestQueue newRequestQueue(Context context, File cacheFile, int maxCacheSizeInBytes) {
        HttpStack stack = null;

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheFile, maxCacheSizeInBytes),
                network);
        queue.start();

        return queue;
    }

    public Cache getHttpDiskCache() {
        if (mHttpRequestQueue != null) {
            return mHttpRequestQueue.getCache();
        }
        return null;
    }
}
