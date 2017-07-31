package example.com.sunshine.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.exoplayer2.C;

import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.PlayActivity;
import example.com.sunshine.R;
import example.com.sunshine.download.Fragment.BaseFragment;
import example.com.sunshine.fragment.AudioVisualizationFragment;

/**
 * Created by qianxiangsen on 2017/7/14.
 */

public class Util {

    public static void setIntnetPlay(FragmentManager fragmentManager,int fragment_container){
        Fragment fragment = PlayActivity.newInstance(ExoConstants.PLAY_URL_NAME);
        addFragment(fragment_container,fragment,fragmentManager);
    }

    public static void addFragment(int fragment_full, Fragment fragments,FragmentManager fragmentManager) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
//                R.animator.fragment_slide_left_exit,
//                R.animator.fragment_slide_right_enter,
//                R.animator.fragment_slide_right_exit);
        ft.replace(fragment_full, fragments);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

}
