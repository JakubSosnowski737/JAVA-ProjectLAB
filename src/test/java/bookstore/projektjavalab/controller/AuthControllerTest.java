package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.dto.AuthResponse;
import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;
import bookstore.projektjavalab.security.JwtTokenProvider;
import bookstore.projektjavalab.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import bookstore.projektjavalab.security.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@Import(AuthControllerTest.DummySecurityConfig.class)
public class AuthControllerTest {

    @TestConfiguration
    static class DummySecurityConfig {
        @Bean
        public JwtTokenProvider jwtTokenProvider() {
            return new JwtTokenProvider();
        }
        @Bean
        public CustomUserDetailsService customUserDetailsService() {
            return Mockito.mock(CustomUserDetailsService.class);
        }
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private AuthService authService;

    @Test
    @DisplayName("POST /api/auth/register - poprawna rejestracja")
    void register_success() throws Exception {
        RegisterRequest req = new RegisterRequest("user123", "user@example.com", "password123");
        // Rejestracja zwraca tekst, nie AuthResponse
        when(authService.register(any(RegisterRequest.class)))
                .thenReturn("Rejestracja zakończona powodzeniem");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Rejestracja zakończona powodzeniem"));
    }

    @Test
    @DisplayName("POST /api/auth/register - walidacja (zły email)")
    void register_badEmail() throws Exception {
        RegisterRequest req = new RegisterRequest("user123", "zlyEmail", "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/register - walidacja (za krótki username)")
    void register_shortUsername() throws Exception {
        RegisterRequest req = new RegisterRequest("ab", "user@example.com", "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - poprawne logowanie")
    void login_success() throws Exception {
        LoginRequest req = new LoginRequest("user123", "password123");
        String fakeToken = "mocked-token";
        when(authService.login(any(LoginRequest.class))).thenReturn(new AuthResponse(fakeToken));
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(fakeToken));
    }

    @Test
    @DisplayName("POST /api/auth/login - niepoprawne dane logowania (błąd)")
    void login_badCredentials() throws Exception {
        LoginRequest req = new LoginRequest("user123", "wrongpass");
        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Błędny login lub hasło"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/auth/login - walidacja (za krótki username)")
    void login_shortUsername() throws Exception {
        LoginRequest req = new LoginRequest("ab", "password123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - walidacja (puste hasło)")
    void login_emptyPassword() throws Exception {
        LoginRequest req = new LoginRequest("user123", "");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
