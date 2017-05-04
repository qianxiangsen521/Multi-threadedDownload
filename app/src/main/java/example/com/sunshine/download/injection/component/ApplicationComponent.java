package example.com.sunshine.download.injection.component;

import android.app.Application;
import android.content.Context;


import javax.inject.Singleton;

import dagger.Component;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.injection.ApplicationContext;
import example.com.sunshine.download.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(TLiveApplication androidBoilerplateApplication);

    @ApplicationContext
    Context context();
    Application application();


}
