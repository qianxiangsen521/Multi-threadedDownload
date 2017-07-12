package example.com.sunshine.download.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import example.com.sunshine.download.Application.TLiveApplication;


/**
 * 网络工具类
 */
public class NetWorkUtil {

    private NetWorkUtil() {

    }

    public static boolean isNetworkConnected() {

        if (TLiveApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) TLiveApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 检查网络状态
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }

    public static boolean isWifiConnected() {

        if (TLiveApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) TLiveApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {

        if (TLiveApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) TLiveApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {

        if (TLiveApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) TLiveApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
