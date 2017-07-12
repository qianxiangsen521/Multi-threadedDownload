package example.com.sunshine.download.Http;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import example.com.sunshine.download.Application.TLiveApplication;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHttp拦截器用来添加公共参数
 *
 */

public class CommonIntercepter implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {


        Request oldRequest = chain.request();


        //添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        Request newRequest;
        Request.Builder newBuilder = oldRequest.newBuilder();
        newBuilder.url(authorizedUrlBuilder.build());
        newBuilder.addHeader("Content-type", "application/json");

        if (oldRequest.method().equals("GET")) {
            newRequest = newBuilder.method(oldRequest.method(), oldRequest.body()).build();
        } else if (oldRequest.method().equals("POST")) {
            newRequest = newBuilder.method(oldRequest.method(), addParmasToFormBody((FormBody) oldRequest.body())).build();
        } else {
            newRequest = newBuilder.build();
        }


        return chain.proceed(newRequest);
    }


    private RequestBody addParmasToFormBody(FormBody formBody) {

        FormBody.Builder builder = new FormBody.Builder();
        if (formBody == null) {
            return builder.build();
        }
        //公共参数
        String sn = SystemUtils.getMd5UniqueID(TLiveApplication.getInstance());
        String sid = "1";
        String platform = "0";
        String channelId = "CNRL010001";
        String imei = SystemUtils.getIMEI(TLiveApplication.getInstance());
        int version = SystemUtils.getAppVersionCode(TLiveApplication.getInstance());
        builder.add("sn", sn);
        builder.add("platform", platform);
        builder.add("sid", sid);
        builder.add("channelId", channelId);
        builder.add("imei", imei);
        builder.add("userId",  "1");
        builder.add("userType",  "2");
        builder.add("imsi", SystemUtils.getImsi(TLiveApplication.getInstance()) + "");
        builder.add("version", version + "");
        for (int i = 0; i < formBody.size(); i++) {
            builder.add(formBody.name(i), formBody.value(i));
        }
        for (int i = 0; i < formBody.size(); i++) {
            builder.add(formBody.name(i), formBody.value(i));
        }

        return builder.build();


    }


}
