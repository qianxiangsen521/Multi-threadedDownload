package example.com.sunshine.download.Presenter;

/**
 * Created by qianxiangsen on 2017/7/4.
 */

public interface RecommendPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();
}
