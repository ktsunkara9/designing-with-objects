package inc.skt.designingwithobjects.car.model;

public class Car {
    private DriverSection driverSection;
    private Engine engine;
    private FuelTank fuelTank;
    private LightSystem lightSystem;
    private Body body;
    private Wheel[] wheels;

    public Car() {
    }
    
    public Car(DriverSection driverSection, Engine engine, FuelTank fuelTank,
               LightSystem lightSystem, Body body, Wheel[] wheels) {
        this.driverSection = driverSection;
        this.engine = engine;
        this.fuelTank = fuelTank;
        this.lightSystem = lightSystem;
        this.body = body;
        this.wheels = wheels;
    }

    public DriverSection getDriverSection() {
        return driverSection;
    }

    public void setDriverSection(DriverSection driverSection) {
        this.driverSection = driverSection;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public FuelTank getFuelTank() {
        return fuelTank;
    }

    public void setFuelTank(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    public LightSystem getLightSystem() {
        return lightSystem;
    }

    public void setLightSystem(LightSystem lightSystem) {
        this.lightSystem = lightSystem;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Wheel[] getWheels() {
        return wheels;
    }

    public void setWheels(Wheel[] wheels) {
        this.wheels = wheels;
    }

    public void start() {
        System.out.println("Car is starting...");
    }

    public void stop() {
        System.out.println("Car is stopping...");
    }

    public void drive() {
        System.out.println("Car is driving...");
    }

    public void brake() {
        System.out.println("Car is braking...");
    }



}