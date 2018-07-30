package example.com.sunshine.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.exoplayer2.C;

import java.util.List;

import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.PlayActivity;
import example.com.sunshine.R;
import example.com.sunshine.download.Fragment.BaseFragment;
import example.com.sunshine.fragment.AudioVisualizationFragment;

/**
 * Created by qianxiangsen on 2017/7/14.
 */

public class Util {

    public static void setIntnetPlay(FragmentManager fragmentManager,int fragment_container,String tag){
        Fragment fragmentNow = fragmentManager.findFragmentByTag(tag);
        if (fragmentNow != null) {
                fragmentManager.beginTransaction().show(fragmentNow).commit();
        }else {
            Fragment fragment = PlayActivity.newInstance(ExoConstants.PLAY_URL_NAME);
            addFragment(fragment_container,fragment,fragmentManager,tag);
        }

    }

    public static void addFragment(int fragment_full, Fragment fragments,FragmentManager fragmentManager,String tag) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
//                R.animator.fragment_slide_left_exit,
//                R.animator.fragment_slide_right_enter,
//                R.animator.fragment_slide_right_exit);
        ft.replace(fragment_full, fragments,tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }


        return true;
    }

    public final static String DOWN_TAG_FRAGMENT = "DWON";

    public final static String PLAY_TAG_FRAGMENT = "PLAY";
}
