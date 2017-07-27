package example.com.sunshine.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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

    public static void addFragment(FragmentManager fragmentManager,int fragment_container,Fragment fragments,String name){

        Fragment fragmentNow = fragmentManager.findFragmentByTag(name
                );
        if (fragmentNow != null) {

            fragmentManager.beginTransaction().hide(fragmentNow).commit();
        }
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment == null) {
            fragment = fragments;
            fragmentManager
                    .beginTransaction()
                    .add(fragment_container, fragment,
                            name).commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }
    public static void setIntnetPlay(FragmentManager fragmentManager,int fragment_container){
        PlayActivity fragment = new PlayActivity();
        Bundle bundle = new Bundle();
        bundle.putString("url",ExoConstants.PLAY_URL_NAME);
        fragment.setArguments(bundle);
        addFragment(fragment_container,fragment,fragmentManager);
    }

    public static void addFragment(int fragment_full, BaseFragment fragments,FragmentManager fragmentManager) {

        Fragment fragmentNow = fragmentManager.findFragmentByTag(fragments.getClass().getSimpleName()
        );
        if (fragmentNow != null) {

            fragmentManager.beginTransaction().hide(fragmentNow).commit();
        }
        Fragment fragment = fragmentManager.findFragmentByTag(fragments.getClass().getSimpleName());
        if (fragment == null) {
            fragment = fragments;
            fragmentManager
                    .beginTransaction()
                    .add(fragment_full, fragment,
                            fragments.getClass().getSimpleName()).addToBackStack(fragments.getClass().getSimpleName())
                    .commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }

}
