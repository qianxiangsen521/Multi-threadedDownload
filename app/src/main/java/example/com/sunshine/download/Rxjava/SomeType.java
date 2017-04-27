package example.com.sunshine.download.Rxjava;


import io.reactivex.Observable;

/**
 * Created by qianxiangsen on 2017/4/27.
 */

public class SomeType {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
        public Observable<String> justObservable() {
        return Observable.just(value);
    }
}
