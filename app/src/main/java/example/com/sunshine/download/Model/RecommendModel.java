package example.com.sunshine.download.Model;

import android.content.Context;

/**
 * Created by qianxiangsen on 2017/7/4.
 */

public interface RecommendModel {


    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);
}
