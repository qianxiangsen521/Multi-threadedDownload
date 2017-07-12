package example.com.sunshine.download.Http;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by qianxiangsen on 2017/5/9.
 */

public interface HttpCallback {


     void UiStart(BaseResponse baseResponse);

     void UiEnd();

     void UiError();

}
