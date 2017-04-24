package example.com.sunshine.download;

/**
 * Created by qianxiangsen on 2017/4/21.
 */

public interface DownloadUiListener {

    void UiStrat();

    void UiProgress(Task task ,long TotalSize ,int DownloadSize);

    void UiFinish(Task task);
}
