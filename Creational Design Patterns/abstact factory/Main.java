// ABSTRACT FACTORY PATTERN
// -------------------------
// The core idea: you have FAMILIES of related objects (here, "payment gateway + invoice"
// per region), and you want to create a whole family together without the client code
// knowing which concrete classes it's dealing with.
//
// Compare this to a plain Factory pattern, which only makes ONE type of object.
// Abstract Factory makes a FAMILY of objects that are meant to work together.
// If you swap the factory (IndiaFactory -> USFactory), every object it produces
// switches consistently — you'll never end up with a Razorpay gateway paired with
// a US invoice by accident. That consistency is the whole point of this pattern.

// ===================================================================
// STEP 1: PRODUCT INTERFACES
// These define WHAT a product family looks like, not how it's built.
// Every region will need something that can process a payment, and
// something that can generate an invoice.
// ===================================================================

interface PaymentGateway {
    void processPayment(double amount);
}

interface Invoice {
    void generateInvoice();
}

// ===================================================================
// STEP 2: CONCRETE PRODUCTS - INDIA FAMILY
// These are the actual implementations that belong together for India.
// Notice they don't know about each other — RazorpayGateway has no idea
// GSTInvoice exists. The factory is what ties them together later.
// ===================================================================

class RazorpayGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via Razorpay: " + amount);
    }
}

class PayUGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via PayU: " + amount);
    }
}

class GSTInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating GST Invoice for India.");
    }
}

// ===================================================================
// STEP 3: CONCRETE PRODUCTS - US FAMILY
// Same deal, just the US versions. This is the second "family" of
// related objects that the pattern needs to make sense — with only
// one family, you wouldn't need Abstract Factory at all.
// ===================================================================

class PayPalGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via PayPal: " + amount);
    }
}

class StripeGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via Stripe: " + amount);
    }
}

class USInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating Invoice as per US norms.");
    }
}

// ===================================================================
// STEP 4: THE ABSTRACT FACTORY INTERFACE
// This is the "factory of factories" contract. It says: whatever
// region you are, you must be able to hand out a PaymentGateway and
// an Invoice. It says nothing about WHICH concrete classes — that's
// left to whoever implements this interface.
// ===================================================================

interface RegionFactory {
    PaymentGateway createPaymentGateway(String gatewayType);

    Invoice createInvoice();
}

// ===================================================================
// STEP 5: CONCRETE FACTORIES
// Each concrete factory is responsible for exactly one product family.
// IndiaFactory only ever hands out India-flavored objects, USFactory
// only ever hands out US-flavored objects. This is where the "family"
// grouping actually gets enforced.
// ===================================================================

class IndiaFactory implements RegionFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        // gatewayType picks WHICH product within the India family to build.
        // This little if-else is technically a Simple Factory living inside
        // the Abstract Factory — pretty common in real code.
        if (gatewayType.equalsIgnoreCase("razorpay")) {
            return new RazorpayGateway();
        } else if (gatewayType.equalsIgnoreCase("payu")) {
            return new PayUGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for India: " + gatewayType);
    }

    public Invoice createInvoice() {
        // No branching needed here — India only has one invoice type (GST),
        // so this factory method always returns the same kind of object.
        return new GSTInvoice();
    }
}

class USFactory implements RegionFactory {
    public PaymentGateway createPaymentGateway(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("paypal")) {
            return new PayPalGateway();
        } else if (gatewayType.equalsIgnoreCase("stripe")) {
            return new StripeGateway();
        }
        throw new IllegalArgumentException("Unsupported gateway for US: " + gatewayType);
    }

    public Invoice createInvoice() {
        return new USInvoice();
    }
}

// ===================================================================
// STEP 6: THE CLIENT
// CheckoutService is the class that actually USES the products. This
// is the whole payoff of the pattern — CheckoutService only talks to
// the PaymentGateway and Invoice interfaces. It has zero knowledge of
// Razorpay, PayPal, GST, or anything concrete. Swap the factory passed
// into the constructor, and this class's behavior changes completely
// without a single line of it being touched.
// ===================================================================

class CheckoutService {
    private PaymentGateway paymentGateway;
    private Invoice invoice;
    private String gatewayType;

    public CheckoutService(RegionFactory factory, String gatewayType) {
        this.gatewayType = gatewayType;
        // The factory decides the concrete classes here — CheckoutService
        // just asks for "a payment gateway" and "an invoice" and trusts
        // whatever comes back matches the region it was given.
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount) {
        // Neither of these lines cares if it's India or US under the hood.
        // That's the decoupling Abstract Factory is buying you.
        paymentGateway.processPayment(amount);
        invoice.generateInvoice();
    }
}

// ===================================================================
// STEP 7: DEMO
// This is where you actually see the family-swapping in action.
// Same CheckoutService class, two completely different factories
// passed in, two completely different object families produced.
// ===================================================================

class Main {
    public static void main(String[] args) {

        // India family: RazorpayGateway + GSTInvoice, wired together by IndiaFactory
        CheckoutService indiaCheckout = new CheckoutService(new IndiaFactory(), "razorpay");
        indiaCheckout.completeOrder(1999.0);

        System.out.println("---");

        // US family: PayPalGateway + USInvoice, wired together by USFactory
        CheckoutService usCheckout = new CheckoutService(new USFactory(), "paypal");
        usCheckout.completeOrder(49.99);
    }
}

// ===================================================================
// QUICK RECAP FOR INTERVIEWS
// ===================================================================
// - Abstract Factory groups multiple related factory methods
// (createPaymentGateway,
// createInvoice) under ONE interface (RegionFactory), so a single factory
// object
// produces a whole matching set of products.
// - This differs from plain Factory Method, where you'd typically have just one
// creation method per factory.
// - The client (CheckoutService) depends only on abstractions (PaymentGateway,
// Invoice, RegionFactory) — never on concrete classes. That's what makes it
// open for extension: adding a "EuropeFactory" later means zero changes to
// CheckoutService.
// - Trade-off worth mentioning in an interview: adding a brand NEW product type
// (say, "TaxReport") means touching the RegionFactory interface and every
// single concrete factory. Abstract Factory makes adding new FAMILIES easy,
// but adding a new PRODUCT across all families is more work.