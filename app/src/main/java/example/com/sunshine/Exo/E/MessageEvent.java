package example.com.sunshine.Exo.E;

import android.support.annotation.NonNull;

public class MessageEvent {

    private long mCurrentPosition;
    private long mDuration;
    private long mBufferedPosition;


    public MessageEvent(@NonNull long mCurrentPosition,
                        @NonNull long mDuration,
                        @NonNull long mBufferedPosition){
        this.mCurrentPosition = mCurrentPosition;
        this.mDuration = mDuration;
        this.mBufferedPosition = mBufferedPosition;


    }


    public long getmBufferedPosition() {
        return mBufferedPosition;
    }

    public long getmCurrentPosition() {
        return mCurrentPosition;
    }

    public long getmDuration() {
        return mDuration;
    }
}