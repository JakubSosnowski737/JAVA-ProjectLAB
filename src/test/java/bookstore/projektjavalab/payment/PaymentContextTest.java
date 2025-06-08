package bookstore.projektjavalab.payment;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentContextTest {

    @Test
    void pay_creditCard_shouldUseCreditCardStrategy() {
        PaymentStrategy ccStrategy = mock(PaymentStrategy.class);
        Map<String, PaymentStrategy> strategies = new HashMap<>();
        strategies.put("creditCard", ccStrategy);

        PaymentContext ctx = new PaymentContext(strategies);
        ctx.pay("creditCard", new BigDecimal("99.99"));

        verify(ccStrategy).pay(new BigDecimal("99.99"));
    }

    @Test
    void pay_paypal_shouldUsePaypalStrategy() {
        PaymentStrategy paypal = mock(PaymentStrategy.class);
        Map<String, PaymentStrategy> strategies = new HashMap<>();
        strategies.put("paypal", paypal);

        PaymentContext ctx = new PaymentContext(strategies);
        ctx.pay("paypal", new BigDecimal("49.99"));

        verify(paypal).pay(new BigDecimal("49.99"));
    }

    @Test
    void pay_unknownStrategy_shouldThrowException() {
        Map<String, PaymentStrategy> strategies = new HashMap<>();
        PaymentContext ctx = new PaymentContext(strategies);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                ctx.pay("bitcoin", new BigDecimal("1.23"))
        );
        assertTrue(ex.getMessage().contains("Nieobsługiwana metoda płatności"));
    }
}
