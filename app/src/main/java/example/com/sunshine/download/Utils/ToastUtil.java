/**
 * 
 */

package example.com.sunshine.download.Utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtil {

	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable runnable = new Runnable() {

		@Override
		public void run() {
			mToast.cancel();
			mToast = null;
		}
	};

	public static void showToast(Context context, String message) {
		mHandler.removeCallbacks(runnable);
		if (mToast == null) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
		mHandler.postDelayed(runnable, 1000);
	}

	public static void show(Context context, String info) {
		showToast(context, info);
	}

	public static void show(Context context, int info) {
		show(context, context.getResources().getString(info));
	}
}
