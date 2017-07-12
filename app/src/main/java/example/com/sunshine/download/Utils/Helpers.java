package example.com.sunshine.download.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import example.com.sunshine.download.Application.TLiveApplication;


public class Helpers {
    @SuppressWarnings("deprecation")
    public static Drawable matrixImage(Bitmap bitmap, int newWidth,
                                       int newHeight) {
        if (null != bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int new_Width = newWidth;
            int new_Height = newHeight;
            float scaleWidth = ((float) new_Width) / width;
            float scaleHeight = ((float) new_Height) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                    height, matrix, true);
            Drawable drawable = new BitmapDrawable(resizedBitmap);
            return drawable;
        }

        return null;

    }

    public static Bitmap matrixBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        if (null != bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int new_Width = width > newWidth ? newWidth : width;
            int new_Height = height > newHeight ? newHeight : height;
            float scaleWidth = ((float) new_Width) / width;
            float scaleHeight = ((float) new_Height) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        }

        return null;

    }

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                * paramContext.getResources().getDisplayMetrics().density);
    }

    // public static boolean isNetworkAvailable(Context context) {
    //
    // /* 【WLAN和3G、GSM等网络】 */
    // ConnectivityManager cwjManager = (ConnectivityManager) context
    // .getSystemService(Context.CONNECTIVITY_SERVICE);
    // NetworkInfo info = cwjManager.getActiveNetworkInfo();
    // if (null == info || !info.isAvailable()) {
    // return false;
    // }
    // return true;
    // }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWifiNetworkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isAvailable();
    }



}
