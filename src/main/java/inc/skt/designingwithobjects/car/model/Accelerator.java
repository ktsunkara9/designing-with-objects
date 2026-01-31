package inc.skt.designingwithobjects.car.model;

public class Accelerator {
    private boolean pressed;

    public Accelerator() {
    }

    public Accelerator(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void accelerate() {
        this.pressed = true;
    }

    public void release() {
        this.pressed = false;
    }

}
