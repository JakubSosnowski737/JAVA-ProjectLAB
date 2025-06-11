package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.AuthResponse;
import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void register_returnsMessage() {
        AuthService authService = Mockito.mock(AuthService.class);
        when(authService.register(any(RegisterRequest.class)))
                .thenReturn("Rejestracja zakończona powodzeniem");

        String result = authService.register(new RegisterRequest("user", "mail@mail.com", "password"));
        assertEquals("Rejestracja zakończona powodzeniem", result);
    }

    @Test
    void login_returnsAuthResponse() {
        AuthService authService = Mockito.mock(AuthService.class);
        when(authService.login(any(LoginRequest.class)))
                .thenReturn(new AuthResponse("mocked-token"));

        AuthResponse response = authService.login(new LoginRequest("user", "password"));
        assertEquals("mocked-token", response.getToken());
    }
}
