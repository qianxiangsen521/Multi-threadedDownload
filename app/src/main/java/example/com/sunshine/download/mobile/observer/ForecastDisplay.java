package example.com.sunshine.download.mobile.observer;

import android.util.Log;

import example.com.sunshine.download.mobile.subject.Subject;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public class ForecastDisplay implements Observer ,DisplayElement {


    private Subject weatherData;
    private float temperature;
    private float humidity;
    public ForecastDisplay(Subject weatherData){
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {

        this.temperature  = temp;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        Log.d("TAGTAG", "ForecastDisplay: "+temperature+"---"+humidity);

    }
}
