package example.com.sunshine.download;



public class DownloadManagerListenerModerator {

    private DownloadListener downloadManagerListener;

    public DownloadManagerListenerModerator(DownloadListener listener){
        downloadManagerListener = listener;
    }

    public void OnDownloadIdieed(Task taskId) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onIdie(taskId);
        }
    }

    public synchronized void OnDownloadErrered(Task taskId) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onError(taskId);
        }
    }

    public  synchronized void onDownloadProcess(Task taskId) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onProgress(taskId);
        }
    }

    public synchronized void OnDownloadFinished(Task taskId) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onComplete(taskId);
        }
    }

    public synchronized void OnDownloadRebuildStart(Task taskId) {
        if (downloadManagerListener != null) {
            downloadManagerListener.onStart(taskId);
        }
    }

}
