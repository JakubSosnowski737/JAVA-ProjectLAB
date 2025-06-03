// src/main/java/bookstore/projektjavalab/dto/RegisterRequest.java
package bookstore.projektjavalab.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
