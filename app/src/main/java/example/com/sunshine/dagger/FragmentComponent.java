package example.com.sunshine.dagger;

import dagger.Component;
import example.com.sunshine.download.Fragment.RecommendFragment;

/**
 * Created by qianxiangsen on 2017/7/5.
 */

@Component(modules = {FragmentMobule.class})
public interface FragmentComponent {

    void inject(RecommendFragment recommendFragment);
}
