package example.com.sunshine.download.request;


import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by xingzhiqiao on 16/3/25.
 */
public class SplashResponse extends BaseResponse {

    private String startup_img;

    public String getStartup_img() {
        return startup_img;
    }

    public void setStartup_img(String startup_img) {
        this.startup_img = startup_img;
    }
}
