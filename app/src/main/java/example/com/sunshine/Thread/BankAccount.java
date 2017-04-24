package example.com.sunshine.Thread;

/**
 * Created by qianxiangsen on 2017/4/17.
 */

class BankAccount {

    private int balance = 100;

    public int getBalance() {
        return balance;
    }
    public void withdraw(int amount){
        balance = balance - amount;
    }
}
