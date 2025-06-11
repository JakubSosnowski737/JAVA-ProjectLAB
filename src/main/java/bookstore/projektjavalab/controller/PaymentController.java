package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.dto.PaymentRequest;
import bookstore.projektjavalab.payment.PaymentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentContext paymentContext;

    @Autowired
    public PaymentController(PaymentContext paymentContext) {
        this.paymentContext = paymentContext;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PaymentRequest paymentRequest) {
        try {
            String method = paymentRequest.getMethod();
            BigDecimal amount = BigDecimal.valueOf(paymentRequest.getAmount());

            paymentContext.pay(method, amount);

            return ResponseEntity.ok("Płatność " + method + " zrealizowana na kwotę " + amount + ".");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas płatności: " + e.getMessage());
        }
    }
}
