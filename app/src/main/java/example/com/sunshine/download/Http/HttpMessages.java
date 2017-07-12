package example.com.sunshine.download.Http;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import example.com.sunshine.HTTP.HttpMessage;
import example.com.sunshine.download.Http.entity.BaseResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public class HttpMessages{

    private volatile static HttpMessages httpMessages;
    private final FmApiService fmApiService;

    private UiMianSubscriber UiMainListener;
    private HttpMessages(HttpCallback callback){
        if (callback != null){
            UiMainListener = new UiMianSubscriber(callback);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Log.d("TAG", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(new CommonIntercepter());
        builder.addInterceptor(interceptor);
        fmApiService = FmApiService.Factory.create(builder);

    }
    public static HttpMessages getInstance(HttpCallback callback){
        synchronized (HttpMessages.class){
            if (httpMessages == null){
                httpMessages = new HttpMessages(callback);
            }
            return httpMessages;
        }
    }

    public FmApiService getFmApiService() {
        return fmApiService;
    }
    public void rxJava(Observable<? extends BaseResponse> observable){
        rx.Observable<? extends BaseResponse> observable1 =
                observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        observable1.subscribe();
    }
}
