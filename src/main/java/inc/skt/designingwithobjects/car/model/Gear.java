package inc.skt.designingwithobjects.car.model;

public class Gear {
    private int currentGear;
    private int minGear;
    private int maxGear;

    public Gear() {
    }

    public Gear(int currentGear, int minGear, int maxGear) {
        this.currentGear = currentGear;
        this.minGear = minGear;
        this.maxGear = maxGear;
    }

    public int getCurrentGear() {
        return currentGear;
    }

    public void setCurrentGear(int currentGear) {
        this.currentGear = currentGear;
    }

    public int getMinGear() {
        return minGear;
    }

    public void setMinGear(int minGear) {
        this.minGear = minGear;
    }

    public int getMaxGear() {
        return maxGear;
    }

    public void setMaxGear(int maxGear) {
        this.maxGear = maxGear;
    }

    public void shiftUp() {
        if (currentGear < maxGear) {
            currentGear++;
        }
    }

    public void shiftDown() {
        if (currentGear > minGear) {
            currentGear--;
        }
    }

    public void shiftToReverse() {
        currentGear = -1;
    }
}
