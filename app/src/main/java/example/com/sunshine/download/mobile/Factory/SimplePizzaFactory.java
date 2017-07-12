package example.com.sunshine.download.mobile.Factory;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public class SimplePizzaFactory {

    public Pizza createPizza(String type){
        Pizza pizza = null;

        if (type.equals("cheese")){
            pizza = new CheesePizza();
        }else if (type.equals("greek")){
            pizza = new GreekPizza();
        }else if (type.equals("pepperoni")){
            pizza = new PepperoniPizza();
        }
        return pizza;
    }
}
