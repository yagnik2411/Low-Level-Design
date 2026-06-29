interface paymentGateway {
    void pay(String orderId, double amount);
}

class PayUGateway implements paymentGateway {
    public void pay(String orderId, double amount) {
        System.out.println("payment of " + amount + " rupees done by PayU for " + orderId + " .");
    }
}

class RazorpayAPI {
    public void makePayment(String invoiceId, double amountInRupees) {
        System.out.println("payment of " + amountInRupees + " rupees done by RazorPay for " + invoiceId + " .");
    }
}

class RazorPayAdapter implements paymentGateway {
    private RazorpayAPI razorPayAPI;

    public RazorPayAdapter() {
        this.razorPayAPI = new RazorpayAPI();
    }

    public void pay(String orderId, double amount) {
        razorPayAPI.makePayment(orderId, amount);
    }
}

class CheckoutService {
    private paymentGateway paymentGateway;

    public CheckoutService(paymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String orderId, double amount) {
        paymentGateway.pay(orderId, amount);
    }
}

public class Main {

}
