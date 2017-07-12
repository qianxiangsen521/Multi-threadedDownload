package example.com.sunshine.download.Permission;

import android.util.Log;

import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;

public class SampleErrorListener implements PermissionRequestErrorListener {
  @Override public void onError(DexterError error) {
    Log.e("Dexter", "There was an error: " + error.toString());
  }
}
