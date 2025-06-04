package bookstore.projektjavalab.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PaymentContext {

    private final Map<String, PaymentStrategy> strategies;

    public PaymentContext(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public void pay(String method, BigDecimal amount) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Nieobsługiwana metoda płatności: " + method);
        }
        strategy.pay(amount);
    }
}
