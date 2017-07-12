package example.com.sunshine.download.request;

/**
 * Created by xingzhiqiao on 16/3/24.
 */
public class BannerInfo {

    private int playId;
    private int playType;
    private String imgUrl;
    private String titleText;
    private String explainText;
    private String optionalText1;
    private String optionalText2;


    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getExplainText() {
        return explainText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public String getOptionalText1() {
        return optionalText1;
    }

    public void setOptionalText1(String optionalText1) {
        this.optionalText1 = optionalText1;
    }

    public String getOptionalText2() {
        return optionalText2;
    }

    public void setOptionalText2(String optionalText2) {
        this.optionalText2 = optionalText2;
    }
}
