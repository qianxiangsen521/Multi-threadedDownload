package example.com.sunshine.download.mobile.adapter;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public class TurkeyAdapter implements Duck {

    private Turkey turkey;
    public TurkeyAdapter(Turkey turkey){
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 5; i++){
            turkey.fly();
        }
    }
}
