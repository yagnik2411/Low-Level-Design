import java.util.*;

interface MatchingStrategy {
    void match(String riderLocation);
}

class NearestDriverStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("MAtching with the nearest available driver to " + riderLocation);
    }
}

class AirportQueueStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("Matching using FIFO airport queue for " + riderLocation);
    }
}

class SurgePriorityStrategy implements MatchingStrategy {
    @Override
    public void match(String riderLocation) {
        System.out.println("Matching rider using surge pricing priority near" + riderLocation);
    }
}

class RideMatchingService {
    private MatchingStrategy strategy;

    public RideMatchingService(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setMatchingStrategy(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void matchRider(String riderLocation) {
        strategy.match(riderLocation);
    }
}

// Client Code
public class Main {
    public static void main(String[] args) {
        RideMatchingService service = new RideMatchingService(new NearestDriverStrategy());

        // Try different strategies
        service.matchRider("Downtown");
        service.matchRider("City Center");
        service.matchRider("Airport Terminal 1");
    }
}
