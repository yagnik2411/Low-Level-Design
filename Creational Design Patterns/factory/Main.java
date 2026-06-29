interface Logistics {
    void send();
}

class Road implements Logistics {

    public void send() {
        System.out.println("Transport by Road");
    }
}

class Air implements Logistics {

    public void send() {
        System.out.println("Transport by Air");
    }
}

class Train implements Logistics {

    public void send() {
        System.out.println("Transport by Train");
    }
}

class LogisticsFactory {
    public static Logistics getLogistics(String mode) {
        if (mode == "Road")
            return new Road();
        else if (mode == "Train")
            return new Train();
        else
            return new Air();
    }
}

class LogisticsService {
    public void send(String mode) {
        Logistics logistics = LogisticsFactory.getLogistics(mode);
        logistics.send();
    }
}

public class Main {

    public static void main(String[] args) {

    }
}