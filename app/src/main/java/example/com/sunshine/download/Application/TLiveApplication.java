package example.com.sunshine.download.Application;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.android.exoplayer2.BuildConfig;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;


/**
 * Created by Administrator on 2016/11/7.
 */

public class TLiveApplication  extends Application{


    private static TLiveApplication mInstance;

    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;
    public DisplayMetrics mDisplayMetrics;

    protected String userAgent;



    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        userAgent = Util.getUserAgent(this, "Multi-threadedDownload");
        initMetrics();
    }
    private void initMetrics() {
        this.mDisplayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay()
                .getMetrics(this.mDisplayMetrics);
        if (this.mDisplayMetrics.heightPixels > this.mDisplayMetrics.widthPixels) {
            mScreenHeight = this.mDisplayMetrics.heightPixels;
            mScreenWidth = this.mDisplayMetrics.widthPixels;
        } else {
            mScreenHeight = this.mDisplayMetrics.widthPixels;
            mScreenWidth = this.mDisplayMetrics.heightPixels;
        }
    }

    public static TLiveApplication get(Context context) {
        return (TLiveApplication) context.getApplicationContext();
    }

    public static TLiveApplication getInstance() {
        return mInstance;
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }
    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }
    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }


}
