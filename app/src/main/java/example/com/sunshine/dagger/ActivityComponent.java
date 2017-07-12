package example.com.sunshine.dagger;

import dagger.Component;
import example.com.sunshine.download.Home.Main111Activity;

/**
 * Created by qianxiangsen on 2017/7/5.
 */

@Component(modules = ActivityMobule.class)
public interface ActivityComponent {

    void inject(Main111Activity main111Activity);
}
