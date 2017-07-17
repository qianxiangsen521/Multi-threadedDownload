package example.com.sunshine.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.PlayActivity;
import example.com.sunshine.R;
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
    public static void setIntnetPlay(Context context){
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("url", ExoConstants.PLAY_URL_NAME);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_botton_bottom,R.anim.slide_bottom);
    }
}
