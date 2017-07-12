package example.com.sunshine.download.mobile.Factory;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public abstract class PizzaStore {


    public Pizza orderPizza(String type){
        Pizza pizza;

        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        pizza.box();
        return pizza;
    }
    abstract Pizza createPizza(String type);
}
