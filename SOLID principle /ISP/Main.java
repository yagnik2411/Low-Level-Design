// This is example of Interface  Principle which I from Solid principle

interface RiderInterface {
    void bookRide();

    void payRide();
}

interface DriverInterface {
    void acceptRide();

    void endRide();

    void drive();
}

class Rider implements RiderInterface {
    public void bookRide() {

    }

    public void payRide() {

    }
}

class Driver implements DriverInterface {
    public void acceptRide() {
    }

    public void endRide() {
    }

    public void drive() {
    }
}

public class Main {
    public static void main(String[] args) {

    }
}
