package example.com.sunshine.download.injection.component;



import dagger.Component;
import example.com.sunshine.download.Fragment.BaseFragment;
import example.com.sunshine.download.Fragment.HomeFragment;
import example.com.sunshine.download.Home.Main1Activity;
import example.com.sunshine.download.injection.PerActivity;
import example.com.sunshine.download.injection.module.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


    void inject(Main1Activity launcherActivity);
    void inject(HomeFragment homeFragment);

}

