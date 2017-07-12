package example.com.sunshine.download.request;


import java.util.List;

import example.com.sunshine.download.Http.entity.BaseResponse;
import example.com.sunshine.download.Http.entity.RadioInfo;

/**
 * Created by xingzhiqiao on 16/4/26.
 */
public class HomeTopResponse extends BaseResponse {

    private List<RadioInfo> radioInfos;

    public List<RadioInfo> getRadioInfos() {
        return radioInfos;
    }

    public void setRadioInfos(List<RadioInfo> radioInfos) {
        this.radioInfos = radioInfos;
    }
}
