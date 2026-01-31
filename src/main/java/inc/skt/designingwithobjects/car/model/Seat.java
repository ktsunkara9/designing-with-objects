package inc.skt.designingwithobjects.car.model;

public class Seat {
    private SeatType seatType;
    private boolean occupied;

    public Seat() {
    }
    public Seat(SeatType seatType, boolean occupied) {
        this.seatType = seatType;
        this.occupied = occupied;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void sit() {
        this.occupied = true;
    }

    public void vacate() {
        this.occupied = false;
    }
}
