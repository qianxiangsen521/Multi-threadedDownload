package example.com.sunshine.test;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by qianxiangsen on 2017/7/6.
 */

@IntDef(flag = true ,
        value = {Main. DISPLAY_USE_LOGO,
                Main. DISPLAY_SHOW_HOME,
                Main. DISPLAY_HOME_AS_UP,
                Main. DISPLAY_SHOW_TITLE,
                Main. DISPLAY_SHOW_CUSTOM}
        )
@Retention(RetentionPolicy.SOURCE)
public @interface DisplayOptions {

}
