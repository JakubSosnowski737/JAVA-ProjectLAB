package bookstore.projektjavalab.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("paypal")
public class PaypalPayment implements PaymentStrategy {

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Płatność PayPal: kwota = " + amount);
    }
}
