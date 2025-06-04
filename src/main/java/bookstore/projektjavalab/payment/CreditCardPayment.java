package bookstore.projektjavalab.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("creditCard")
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public void pay(BigDecimal amount) {
        // Tu implementacja płatności kartą (w przykładzie wystarczy logika mock)
        System.out.println("Płatność kartą: kwota = " + amount);
    }
}
