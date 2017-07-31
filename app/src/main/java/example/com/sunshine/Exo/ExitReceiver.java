package example.com.sunshine.Exo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExitReceiver  extends BroadcastReceiver {
    public final String ACTION_EXIT_RADIO = "cn.cnr.fm.action.exit";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(ACTION_EXIT_RADIO)){
		    PlayManager.exitApp(context);
		}
	}
}