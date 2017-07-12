package example.com.sunshine.download.mobile.Observer1;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import example.com.sunshine.download.mobile.observer.DisplayElement;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public class CruuentConitionsDisplay implements Observer,DisplayElement {

    Observable observable;
    private float temperature;
    private float humidity;
    public CruuentConitionsDisplay(Observable observable){
        this.observable = observable;
        observable.addObserver(this);

    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof  WeatherData){
            WeatherData weatherData = (WeatherData)o;
            this.humidity = weatherData.getHumidity();
            this.temperature = weatherData.getTemperature();
            display();
        }
    }

    @Override
    public void display() {
        Log.d("TAGTAG", "display: "+"---"+humidity+"0000----"+temperature);
    }
}
