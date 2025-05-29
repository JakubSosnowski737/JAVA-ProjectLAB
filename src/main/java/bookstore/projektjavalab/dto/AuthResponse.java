// src/main/java/bookstore/projektjavalab/dto/AuthResponse.java
package bookstore.projektjavalab.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final String message;

    public AuthResponse(String message) {
        this.message = message;
    }
}
