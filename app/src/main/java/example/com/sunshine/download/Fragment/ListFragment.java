package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import javax.inject.Inject;

import example.com.sunshine.HTTP.HttpMessage;
import example.com.sunshine.R;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.request.VolleySingleton;
import example.com.sunshine.entity.HomeEntity;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class ListFragment extends BaseFragment{

    @Inject
    public ListFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Configuration.HOME_XIMALAYAFM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson mGson = new Gson();
                HomeEntity mHomeEntity = mGson.fromJson(s,HomeEntity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void OnClick(View v) {

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
