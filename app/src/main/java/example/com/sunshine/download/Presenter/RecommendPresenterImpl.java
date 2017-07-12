package example.com.sunshine.download.Presenter;

import android.util.Log;

import javax.inject.Inject;

import example.com.sunshine.download.Model.RecommendModel;
import example.com.sunshine.download.Model.RecommendModelImpl;
import example.com.sunshine.download.View.LoginView;

/**
 * Created by qianxiangsen on 2017/7/4.
 */

public class RecommendPresenterImpl implements RecommendPresenter,RecommendModel.OnLoginFinishedListener {


    private LoginView loginView;
    private RecommendModel loginInteractor;

    @Inject
    public RecommendPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new RecommendModelImpl();
    }

    @Override public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this);
    }

    @Override public void onDestroy() {
        loginView = null;
    }

    @Override public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}