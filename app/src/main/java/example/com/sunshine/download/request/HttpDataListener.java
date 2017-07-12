package example.com.sunshine.download.request;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by xingzhiqiao on 16/3/24.
 */
public interface HttpDataListener {


    void onDataReady(BaseResponse response);

    void onError(ErrorStatus respStatus);

}
