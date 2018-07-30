package example.com.sunshine.Exo.E;

public class ExitEvent {

    private boolean exit;

    public ExitEvent(boolean exit) {
        this.exit = exit;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
