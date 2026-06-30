class PaymentService {
    public void makePayment(String accountId, double amount) {
        System.out.println("Payment of ₹" + amount + " successful for account " + accountId);
    }
}

class SeatReservationService {
    public void reserveSeat(String movieId, String seatNumber) {
        System.out.println("Seat " + seatNumber + " reserved for movie " + movieId);
    }
}

class NotificationService {
    public void sendBookingConfirmation(String userEmail) {
        System.out.println("Booking confirmation sent to " + userEmail);
    }
}

class LoyaltyPointsService {
    public void addPoints(String accountId, int points) {
        System.out.println(points + " loyalty points added to account " + accountId);
    }
}

class TicketService {
    public void generateTicket(String movieId, String seatNumber) {
        System.out.println("Ticket generated for movie " + movieId + ", Seat: " + seatNumber);
    }
}

class MovieBookingFacade {
    private PaymentService paymentService;
    private SeatReservationService seatReservationService;
    private NotificationService notificationService;
    private LoyaltyPointsService loyaltyPointsService;
    private TicketService ticketService;
    
    public MovieBookingFacade() {
        paymentService = new PaymentService();
        seatReservationService = new SeatReservationService();
        notificationService = new NotificationService();
        loyaltyPointsService = new LoyaltyPointsService();
        ticketService = new TicketService();
    }

    public void bookMovieTicket(String accountId, double amount, String movieId, String seatNumber, String userEmail,
            int points) {
        paymentService.makePayment(accountId, amount);
        seatReservationService.reserveSeat(movieId, seatNumber);
        notificationService.sendBookingConfirmation(userEmail);
        loyaltyPointsService.addPoints(accountId, points);
        ticketService.generateTicket(movieId, seatNumber);

    }
}

class Main {
    public static void main(String[] args) {

    }
}
