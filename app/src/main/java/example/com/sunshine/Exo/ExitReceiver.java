package example.com.sunshine.Exo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import example.com.sunshine.Exo.E.ExitEvent;
import example.com.sunshine.util.Util;

public class ExitReceiver  extends BroadcastReceiver {
    public final String ACTION_EXIT_RADIO = "cn.cnr.fm.action.exit";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(ACTION_EXIT_RADIO)){
			if (!Util.isBackgroundRunning(context)){
				EventBus.getDefault().post(new ExitEvent(true));
			}else {
				PlayManager.getInstance().stop();
				PlayManager.destoryInstance();
				System.exit(0);
			}

		}
	}
}