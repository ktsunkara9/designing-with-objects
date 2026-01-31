package inc.skt.designingwithobjects.car.model;

public class Horn {
    private boolean pressed;

    public Horn() {
    }

    public Horn(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void honk() {
        this.pressed = true;
        System.out.println("Honk! Honk!");
    }

    public void release() {
        this.pressed = false;
    }
}
