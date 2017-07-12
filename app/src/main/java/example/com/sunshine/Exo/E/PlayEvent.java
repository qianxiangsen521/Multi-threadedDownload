package example.com.sunshine.Exo.E;

/**
 * Created by qianxiangsen on 2017/7/12.
 */

public class PlayEvent {

    private  boolean isPlayWhenReady;
    public PlayEvent(boolean isPlayWhenReady){
        this.isPlayWhenReady = isPlayWhenReady;
    }

    public boolean isPlayWhenReady() {
        return isPlayWhenReady;
    }
}
