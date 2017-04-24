package example.com.sunshine.HTTP;

public class Adult {
    protected int age = 0;
    protected String firstname = "firstname";
    protected String lastname = "lastname";
    protected String gender = "MALE";
    protected int progress = 0;

    public Adult() {
        super();
    }
    public void move() {
        System.out.println("Moved.");
    }
    public void talk() {
        System.out.println("Spoke.");
    }
}