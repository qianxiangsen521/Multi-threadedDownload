package example.com.sunshine.download;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadFactory;

import example.com.sunshine.HTTP.Utils;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by qianxiangsen on 2017/4/21.
 */

public final class Utlis {

    static final String THREAD_PREFIX = "Picasso-";

    static String formatPercent(long para1,long para2) {
        DecimalFormat df = new DecimalFormat("#.0%");
        String fileSizeString = "";
        if(para1>para2)
        {
            para1 = para2;
        }
        double temp;
        if(para1>0)
        {
            temp = (double)para1/para2;
            fileSizeString = df.format(temp);
        }
        else
        {
            fileSizeString="0.0%";
        }
        return fileSizeString;
    }
    String format(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    /**
     * Format file size to String
     *
     * @param size long
     * @return String
     */
    static String formatSize(long size) {
        String hrSize;
        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" B");
        }
        return hrSize;
    }
    @SuppressWarnings("unchecked")
    static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }

    static boolean hasPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
    static class PicassoThreadFactory implements ThreadFactory {
        @SuppressWarnings("NullableProblems")
        public Thread newThread(Runnable r) {
            return new PicassoThread(r);
        }
    }

    private static class PicassoThread extends Thread {
        public PicassoThread(Runnable r) {
            super(r);
        }

        @Override public void run() {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }

}
