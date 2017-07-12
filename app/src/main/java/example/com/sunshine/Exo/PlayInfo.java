package example.com.sunshine.Exo;

/**
 * Created by qianxiangsen on 2017/7/11.
 */

public class PlayInfo {

    private long playId;

    private long position;

    private String playUrl;


    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPlayId() {
        return playId;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
