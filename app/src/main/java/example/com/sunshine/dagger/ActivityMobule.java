package example.com.sunshine.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qianxiangsen on 2017/7/5.
 */

@Module
public class ActivityMobule {

    private Context context;
    public ActivityMobule(Context context){
        this.context = context;
    }

    @Provides
    Context ProvidesContext() {
        return context;
    }
}
