package bookstore.projektjavalab.payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaymentStrategiesTest {

    @Test
    void creditCardPayment_shouldNotThrow() {
        CreditCardPayment cc = new CreditCardPayment();
        assertDoesNotThrow(() -> cc.pay(new BigDecimal("10.00")));
    }

    @Test
    void paypalPayment_shouldNotThrow() {
        PaypalPayment pp = new PaypalPayment();
        assertDoesNotThrow(() -> pp.pay(new BigDecimal("20.00")));
    }
}
