package bookstore.projektjavalab.dto;

public class PaymentRequest {
    private String method; // "credit_card" lub "paypal"
    private double amount;

    public PaymentRequest() {}

    public PaymentRequest(String method, double amount) {
        this.method = method;
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
