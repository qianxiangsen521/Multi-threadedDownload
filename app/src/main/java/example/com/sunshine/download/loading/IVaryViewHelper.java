package example.com.sunshine.download.loading;

import android.content.Context;
import android.view.View;

public interface IVaryViewHelper {

	View getCurrentLayout();

	void restoreView();

	void showLayout(View view);

	View inflate(int layoutId);

	Context getContext();

	View getView();

}