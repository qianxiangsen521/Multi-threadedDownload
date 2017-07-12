package example.com.sunshine.download.mobile.Observer1;

import java.util.Observable;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public class WeatherData extends Observable{

    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData(){

    }
    public void measurementsChanged(){
        setChanged();
        notifyObservers();
    }
    public void setMeasurements(float temperature,float humidity,float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
