interface PaymentProcessor {
    void processPayment(double amount);
}

class PayPalGateway {
    public void makePayment(double amount) {
        System.out.println("Paid ₹" + amount + " using PayPal.");
    }
}

class StripeGateway {
    public void executePayment(double value) {
        System.out.println("Paid ₹" + value + " using Stripe.");
    }
}

class RazorpayGateway {
    public void doTransaction(double amountInINR) {
        System.out.println("Paid ₹" + amountInINR + " using Razorpay.");
    }
}

class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPal;

    public PayPalAdapter() {
        payPal = new PayPalGateway();
    }

    public void processPayment(double amount) {
        payPal.makePayment(amount);
    }
}

class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripe;

    public StripeAdapter() {
        stripe = new StripeGateway();
    }

    public void processPayment(double amount) {
        stripe.executePayment(amount);
    }
}

class RazorpayAdapter implements PaymentProcessor {
    private RazorpayGateway razorpay;

    public RazorpayAdapter() {
        razorpay = new RazorpayGateway();
    }

    public void processPayment(double amount) {
        razorpay.doTransaction(amount);
    }
}

public class AdapterPatternExample {
    public static void main(String[] args) {
        PaymentProcessor paypal = new PayPalAdapter();
        PaymentProcessor stripe = new StripeAdapter();
        PaymentProcessor razorpay = new RazorpayAdapter();

        paypal.processPayment(2500);
        stripe.processPayment(5000);
        razorpay.processPayment(7500);
    }
}
