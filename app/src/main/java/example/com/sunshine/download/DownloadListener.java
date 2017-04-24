package example.com.sunshine.download;

public interface DownloadListener {
	
	void onIdie(Task request);

	void onStart(Task request);

	void onProgress(Task request);

	void onError(Task request);

	void onComplete(Task request);
}
