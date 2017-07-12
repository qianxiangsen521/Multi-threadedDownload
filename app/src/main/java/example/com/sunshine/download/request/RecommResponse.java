package example.com.sunshine.download.request;


import java.util.List;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by xingzhiqiao on 16/3/24.
 */
public class RecommResponse extends BaseResponse {

    private List<BannerInfo> bannerInfo;

    private BookRecommItem bookingProgram;

    public List<BannerInfo> getBannerinfo() {
        return bannerInfo;
    }

    public void setBannerinfo(List<BannerInfo> bannerinfo) {
        this.bannerInfo = bannerinfo;
    }

    public BookRecommItem getBookingProgram() {
        return bookingProgram;
    }

    public void setBookingProgram(BookRecommItem bookingProgram) {
        this.bookingProgram = bookingProgram;
    }
}
