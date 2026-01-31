package inc.skt.designingwithobjects.car.model;

public class DriverSection {

    private Seat driverSeat;
    private Steering steering;
    private Accelerator accelerator;
    private Brake brake;
    private Gear gear;
    private Horn horn;
    private Mirror mirror;
    private Indicator[] indicators;
    private FuelIndicator fuelIndicator;
    private Speedometer speedometer;
    private Direction direction;

    public DriverSection() {
    }

    public DriverSection(Seat driverSeat, Steering steering, Accelerator accelerator,
                         Brake brake, Gear gear, Horn horn, Mirror mirror,
                         Indicator[] indicators, FuelIndicator fuelIndicator,
                         Speedometer speedometer) {
        this.driverSeat = driverSeat;
        this.steering = steering;
        this.accelerator = accelerator;
        this.brake = brake;
        this.gear = gear;
        this.horn = horn;
        this.mirror = mirror;
        this.indicators = indicators;
        this.fuelIndicator = fuelIndicator;
        this.speedometer = speedometer;
    }

    public Seat getDriverSeat() {
        return driverSeat;
    }

    public void setDriverSeat(Seat driverSeat) {
        this.driverSeat = driverSeat;
    }

    public Steering getSteering() {
        return steering;
    }

    public void setSteering(Steering steering) {
        this.steering = steering;
    }

    public Accelerator getAccelerator() {
        return accelerator;
    }

    public void setAccelerator(Accelerator accelerator) {
        this.accelerator = accelerator;
    }

    public Brake getBrake() {
        return brake;
    }

    public void setBrake(Brake brake) {
        this.brake = brake;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Horn getHorn() {
        return horn;
    }

    public void setHorn(Horn horn) {
        this.horn = horn;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public void setMirror(Mirror mirror) {
        this.mirror = mirror;
    }

    public Indicator[] getIndicators() {
        return indicators;
    }

    public void setIndicators(Indicator[] indicators) {
        this.indicators = indicators;
    }

    public FuelIndicator getFuelIndicator() {
        return fuelIndicator;
    }

    public void setFuelIndicator(FuelIndicator fuelIndicator) {
        this.fuelIndicator = fuelIndicator;
    }

    public Speedometer getSpeedometer() {
        return speedometer;
    }

    public void setSpeedometer(Speedometer speedometer) {
        this.speedometer = speedometer;
    }

    public void accelerate() {
        System.out.println("Driver is accelerating the car...");
    }

    public void brake() {
        System.out.println("Driver is stopping the car...");
    }

    public void steer(String direction) {
        System.out.println("Driver is steering the car to the " + direction + "...");
    }

    public void honk() {
        System.out.println("Driver is honking the horn...");
    }

    public void changeGear(int gearNumber) {
        System.out.println("Driver is changing to gear " + gearNumber + "...");
    }

    public void checkMirrors() {
        System.out.println("Driver is checking the mirrors...");
    }

    public void indicateTurn(String direction) {
        System.out.println("Driver is indicating a turn to the " + direction + "...");
    }

    public void checkFuelLevel() {
        System.out.println("Driver is checking the fuel level...");
    }

    public void checkSpeed() {
        System.out.println("Driver is checking the speed...");
    }

}
