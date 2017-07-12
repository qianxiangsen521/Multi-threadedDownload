package example.com.sunshine.download.mobile.Achieve;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public class ModelCharacter extends Character {


    public ModelCharacter(){
        weaponBehavior = new SwordBehavior();
    }
    @Override
    void fight() {
        weaponBehavior.useWeapon();
    }
}
