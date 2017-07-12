package example.com.sunshine.download.Http;

import java.util.Map;

import example.com.sunshine.download.Http.entity.UserLoginResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qianxiangsen on 2017/5/9.
 */

public interface FmApiService {

    @POST("member/loginUser")
    @FormUrlEncoded
    Observable<UserLoginResponse> getHomeRecommend(@FieldMap Map<String, String> params);


    class Factory {
        public static FmApiService create(OkHttpClient.Builder builder) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl("http://api.cnrmobile.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(FmApiService.class);
        }
    }
}
