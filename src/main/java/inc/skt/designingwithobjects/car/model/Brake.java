package inc.skt.designingwithobjects.car.model;

public class Brake {

    private boolean pressed;
    private BreakingForce breakingForce;

    public Brake() {
    }

    public Brake(boolean isPressed, BreakingForce breakingForce) {
        this.pressed = pressed;
        this.breakingForce = breakingForce;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public BreakingForce getBreakingForce() {
        return breakingForce;
    }

    public void setBreakingForce(BreakingForce breakingForce) {
        this.breakingForce = breakingForce;
    }

}
