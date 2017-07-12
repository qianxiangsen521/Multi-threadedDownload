package example.com.sunshine.download.mobile.singleton;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public class ChocolaeBoiler {

    private boolean empty;
    private boolean boiled;

    public ChocolaeBoiler(){
        empty = true;
        boiled = false;
    }
    public void fill(){
        if (isEmpty()){
            empty = false;
            boiled = false;
        }
    }
    public void drain(){
        if (!isEmpty() && isBoiled()){
            boiled = true;
        }
    }
    public void boil(){
        if (!isEmpty() && isBoiled()){
            boiled = true;
        }
    }

    public boolean isBoiled() {
        return boiled;
    }

    public boolean isEmpty() {
        return empty;
    }
}
