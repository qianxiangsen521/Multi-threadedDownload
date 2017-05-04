package example.com.sunshine.download.Application;

import android.app.Application;
import android.content.Context;

import example.com.sunshine.download.injection.component.ApplicationComponent;
import example.com.sunshine.download.injection.component.DaggerApplicationComponent;
import example.com.sunshine.download.injection.module.ApplicationModule;


/**
 * Created by Administrator on 2016/11/7.
 */

public class TLiveApplication  extends Application{


    private static TLiveApplication mInstance;


    ApplicationComponent mApplicationComponent;

    /**
     * 微信 API
     */

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);


        mInstance = this;
    }

    public static TLiveApplication get(Context context) {
        return (TLiveApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

}
