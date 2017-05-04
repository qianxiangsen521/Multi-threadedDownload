package example.com.sunshine.download.loading;

public interface BaseView {

    void showLoading();

    void refreshView();

    void showNetError();

    void showEmptyView(String msg);
//
//    void hasNoMoreDate();
//
//    void loadMoreFinish(List dates);
//
//    void showRefreshFinish(List score);
//
//    void showToastError();
}
