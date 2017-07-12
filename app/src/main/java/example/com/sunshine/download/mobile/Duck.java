package example.com.sunshine.download.mobile;

import example.com.sunshine.download.mobile.Achieve.FlyWithWings;
import example.com.sunshine.download.mobile.Achieve.MuteQuack;
import example.com.sunshine.download.mobile.Interface.FlyBehavior;
import example.com.sunshine.download.mobile.Interface.QuackBehavior;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public abstract class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    public Duck(){
        flyBehavior = new FlyWithWings();
        quackBehavior = new MuteQuack();

    }
    protected void swim(){

    }
    protected abstract void display();

    public void performFly(){
        flyBehavior.fly();
    }
    public void performQuack(){
        quackBehavior.quack();
    }

}
