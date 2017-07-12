package example.com.sunshine.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.multibindings.IntKey;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/7/6.
 */

public class MainTestActivity extends AppCompatActivity{


    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    @IntDef({SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays{

    }
    @WeekDays
    int currentDay = SUNDAY;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        Main main = new Main();

        main.setTextStyle(Main.DISPLAY_HOME_AS_UP);

//        main.setFlavour(Main.CHOCOLATE);
//
//
//
//        setCurrentDay(WEDNESDAY);
//
//
//
//        @WeekDays int today = getCurrentDay();
       @DisplayOptions
       int today =   main.getTextStyle();
        switch (today) {
            case Main.DISPLAY_HOME_AS_UP:
                Log.d("TAG", "DISPLAY_HOME_AS_UP");
                break;
            case MONDAY:
                Log.d("TAG", "Today is MONDAY");
                break;
//            case TUESDAY:
//                Log.d("TAG", "Today is TUESDAY");
//                break;
//            case WEDNESDAY:
//                Log.d("TAG", "Today is WEDNESDAY");
//                break;
//            case THURSDAY:
//                Log.d("TAG", "Today is THURSDAY");
//                break;
//            case FRIDAY:
//                Log.d("TAG", "Today is FRIDAY");
//                break;
//            case SATURDAY:
//                Log.d("TAG", "Today is SATURDAY");
//                break;
            default:
                break;
        }

    }

    @WeekDays
    public int getCurrentDay() {
        return currentDay;
    }


    public void setCurrentDay(@WeekDays int currentDay) {
        this.currentDay = currentDay;
    }
}
