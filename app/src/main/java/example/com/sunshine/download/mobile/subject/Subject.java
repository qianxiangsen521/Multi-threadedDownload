package example.com.sunshine.download.mobile.subject;

import example.com.sunshine.download.mobile.observer.Observer;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public interface Subject {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
