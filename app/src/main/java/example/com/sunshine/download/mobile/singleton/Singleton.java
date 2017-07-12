package example.com.sunshine.download.mobile.singleton;

/**
 * Created by qianxiangsen on 2017/5/8.
 */

public class Singleton {

    private static Singleton unqueInstance;

    private Singleton(){

    }
    public static Singleton getInstance(){
        if (unqueInstance == null){

            unqueInstance = new Singleton();
        }
        return unqueInstance;
    }

}
