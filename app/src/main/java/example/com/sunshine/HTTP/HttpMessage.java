package example.com.sunshine.HTTP;

import android.content.Context;

/**
 * Created by qianxiangsen on 2017/4/11.
 */

public class HttpMessage {

    static volatile HttpMessage singleton = null;
    static Context context;
    public HttpMessage(Context context){
        if (context != null){
            this.context = context.getApplicationContext();
        }
    }
    public static HttpMessage getSingleton(){
        if (singleton == null){
            synchronized (HttpMessage.class){
                if (singleton == null) {
                    singleton = new HttpMessage(context);
                }
            }
        }
        return singleton;
    }
}
