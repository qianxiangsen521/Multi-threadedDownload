package example.com.sunshine.download.mobile.Achieve;

import example.com.sunshine.download.mobile.Interface.WeaponBehavior;

/**
 * Created by qianxiangsen on 2017/5/5.
 */

public abstract class Character {

    WeaponBehavior weaponBehavior;
    public Character(){
        weaponBehavior = new SwordBehavior();

    }

    abstract void fight();


    public WeaponBehavior getWeaponBehavior() {
        return weaponBehavior;
    }

    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }
}
