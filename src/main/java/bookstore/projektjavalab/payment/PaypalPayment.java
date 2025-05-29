package bookstore.projektjavalab.payment;

public class PaypalPayment implements PaymentStrategy {
    private final String email;

    public PaypalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of " + amount + " for " + email);
    }
}
