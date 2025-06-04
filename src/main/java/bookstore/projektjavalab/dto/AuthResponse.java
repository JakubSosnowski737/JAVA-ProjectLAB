package bookstore.projektjavalab.dto;

public class AuthResponse {

    private String message; // lub: private String token;

    public AuthResponse() {
    }

    public AuthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
