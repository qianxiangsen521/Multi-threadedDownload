package example.com.sunshine.test;

import android.support.annotation.IntDef;

/**
 * Created by qianxiangsen on 2017/7/6.
 */

public class Main {


    public final static   int DISPLAY_USE_LOGO =0;

    public final static  int DISPLAY_SHOW_HOME = 1;
    public final static  int DISPLAY_HOME_AS_UP = 2;
    public final static  int DISPLAY_SHOW_TITLE = 3;
    public final static int DISPLAY_SHOW_CUSTOM = 4;

    private int TextStyle = Main.DISPLAY_USE_LOGO;


    @DisplayOptions
    public int getTextStyle() {
        return TextStyle;
    }

    public void setTextStyle(@DisplayOptions int textStyle) {
        TextStyle = textStyle;
    }

    private int flavour;

    public static final int VANILLA = 0;
    public static final int CHOCOLATE = 1;
    public static final int STRAWBERRY = 2;

    @IntDef({VANILLA, CHOCOLATE, STRAWBERRY})
    public @interface Flavour {
    }

    @Flavour
    public int getFlavour() {
        return flavour;
    }

    public void setFlavour(@Flavour int flavour) {
        this.flavour = flavour;
    }
//
//    public enum WeekDays{
//        SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
//    }
//    private WeekDays currentDays = WeekDays.SUNDAY;
//
//    public WeekDays getCurrentDays() {
//        return currentDays;
//    }
//
//    public void setCurrentDays(WeekDays currentDays) {
//        this.currentDays = currentDays;
//    }

}
