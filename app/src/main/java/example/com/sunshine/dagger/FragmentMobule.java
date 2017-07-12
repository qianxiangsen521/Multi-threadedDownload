package example.com.sunshine.dagger;

import dagger.Module;
import dagger.Provides;
import example.com.sunshine.download.View.LoginView;

/**
 * Created by qianxiangsen on 2017/7/5.
 */

@Module
public class FragmentMobule {


    private LoginView loginView;
    public FragmentMobule(LoginView loginView){
        this.loginView = loginView;

    }

    @Provides
    LoginView ProvidesloginView() {
        return loginView;
    }
}
