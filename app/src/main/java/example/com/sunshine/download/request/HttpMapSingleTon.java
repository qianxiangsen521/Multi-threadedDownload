package example.com.sunshine.download.request;

import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;

import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * map对应类（通过url可对应到所返回的类，通过类命能够获取到请求的url）
 * Created by xingzhiqiao on 16/3/7.
 */
public class HttpMapSingleTon {


    private static HttpMapSingleTon mInstance;


    public static String GAME = "http://api.tv.test.cnrmobile.com/playdetail/getgamenavlist";

    public static String RECOMMEND = "http://api.tv.cnrmobile.com/mobileapi/main";


    private HashMap<String, Class<? extends BaseResponse>> httpMap = new HashMap<String, Class<? extends BaseResponse>>();

    private HashMap<Class<? extends BaseResponse>, String> httpMapReverse = new HashMap<Class<? extends BaseResponse>, String>();

    // 是否开启加密传输模式
    private boolean encodeMode = true;

    // 加密传输的url
    private static ArrayList<String> encodeShortUrls = new ArrayList<String>();


    private HttpMapSingleTon() {

        httpMap.put(RECOMMEND, RecommResponse.class);
//        httpMap.put(Configuration.SPLASH_ENERALIZE_URL, SplashResponse.class);
        httpMap.put(Configuration.HOME_HEADER_RUL, HomeTopResponse.class);
        httpMap.put(Configuration.HOME_BOTTOM_URL, HomeBottomResponse.class);
        httpMap.put(Configuration.CATEGORY_INDEX, CategoryResponse.class);
//        httpMap.put(Configuration.HOME_COMMON_MORE, HomeMoreResponse.class);
        for (String key : httpMap.keySet()) {
            httpMapReverse.put(httpMap.get(key), key);
        }
    }

    public static HttpMapSingleTon getInstance() {
        if (mInstance == null) {
            mInstance = new HttpMapSingleTon();
        }
        return mInstance;
    }

    /**
     * 获取HttpResponse对象
     */
    public Class<? extends BaseResponse> getHttpResponseType(String name) {

        if (httpMap.containsKey(name)) {
            return httpMap.get(name);
        } else {
            for (String key : httpMap.keySet()) {
                if (name.startsWith(key)) {
                    return httpMap.get(key);
                }
            }
            return null;
        }

//        return httpMap.get(name);
    }

    /**
     * 根据HttpResponse对象得到请求shortUrl
     */
    public String getHttpShortUrl(BaseResponse mBaseResponse) {
        String shortUrl = "";
        try {
            Log.e("json:", "mBaseResponse class:" + mBaseResponse.getClass().toString());
            shortUrl = httpMapReverse.get(mBaseResponse.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shortUrl;
    }

    public static boolean needEncode(String shortUrl) {
        for (String encodeShortUrl : encodeShortUrls) {
            if (shortUrl.equals(encodeShortUrl)) {
                return true;
            }
        }
        return false;
    }
}
