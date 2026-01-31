package inc.skt.designingwithobjects.car.model;

public class Steering {
    private Direction direction;

    public Steering() {
    }

    public Steering(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void turnLeft() {
        this.direction = Direction.LEFT;
    }

    public void turnRight() {
        this.direction = Direction.RIGHT;
    }

    public void moveStraight() {
        this.direction = Direction.STRAIGHT;
    }
}
