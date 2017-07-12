package example.com.sunshine.download.mobile.Achieve;

import example.com.sunshine.download.mobile.Duck;
import example.com.sunshine.download.mobile.Interface.FlyBehavior;
import example.com.sunshine.download.mobile.Interface.Flyable;
import example.com.sunshine.download.mobile.Interface.QuackBehavior;
import example.com.sunshine.download.mobile.Interface.Quackable;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public class MallardDuck extends Duck {

    QuackBehavior quackBehavior;
    FlyBehavior flyBehavior;
    Duck duck;
   public MallardDuck(){
       quackBehavior = new Quack();
       flyBehavior = new FlyWithWings();

       duck = new MallardDuck();
       duck.performFly();
       duck.performQuack();


   }

    @Override
    protected void display() {

    }

}
