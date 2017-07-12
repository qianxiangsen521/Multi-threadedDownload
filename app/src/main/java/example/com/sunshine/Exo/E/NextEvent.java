package example.com.sunshine.Exo.E;

/**
 * Created by qianxiangsen on 2017/7/12.
 */

public class NextEvent {

    private boolean isSeekable;
    private boolean enablePrevious;
    private boolean enableNext;
    public NextEvent(boolean isSeekable,boolean enablePrevious,boolean enableNext){
        this.isSeekable = isSeekable;
        this.enableNext = enableNext;
        this.enablePrevious = enablePrevious;

    }

    public boolean isSeekable() {
        return isSeekable;
    }

    public boolean isEnablePrevious() {
        return enablePrevious;
    }

    public boolean isEnableNext() {
        return enableNext;
    }
}
