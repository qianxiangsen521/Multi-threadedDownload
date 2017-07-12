package example.com.sunshine.download.Http;

import example.com.sunshine.download.Http.entity.BaseResponse;
import rx.Subscriber;

/**
 * Created by qianxiangsen on 2017/5/9.
 */

public class UiMianSubscriber extends Subscriber<BaseResponse> {


    private HttpCallback callback;
    public UiMianSubscriber(HttpCallback callback){
        this.callback = callback;

    }
    @Override
    public void onError(Throwable e) {
        callback.UiError();

    }

    @Override
    public void onNext(BaseResponse baseResponse) {
        callback.UiStart(baseResponse);

    }

    @Override
    public void onCompleted() {
        callback.UiEnd();

    }
}
