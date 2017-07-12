package example.com.sunshine.download.View;

/**
 * Created by qianxiangsen on 2017/7/4.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

}
