package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;
import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.RoleRepository;
import bookstore.projektjavalab.repository.UserRepository;
import bookstore.projektjavalab.security.JwtTokenProvider;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder encoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepository.class);
        roleRepo = mock(RoleRepository.class);
        encoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        authService = new AuthService(userRepo, jwtTokenProvider, encoder);
    }

    @Test
    void register_shouldSaveUser_whenUsernameNotExists() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("jan");
        req.setEmail("jan@example.com");
        req.setPassword("tajne");

        when(userRepo.findByUsername("jan")).thenReturn(Optional.empty());
        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(encoder.encode("tajne")).thenReturn("zakodowane");

        authService.register(req);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());
        User saved = userCaptor.getValue();
        assertThat(saved.getUsername()).isEqualTo("jan");
        assertThat(saved.getEmail()).isEqualTo("jan@example.com");
        assertThat(saved.getPassword()).isEqualTo("zakodowane");
        assertThat(saved.getRoles()).contains(role);
    }

    @Test
    void register_shouldThrowException_whenUserExists() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("jan");
        when(userRepo.findByUsername("jan")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> authService.register(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Użytkownik już istnieje");
        verify(userRepo, never()).save(any());
    }

    @Test
    void register_shouldThrowException_whenRoleNotFound() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("jan");
        when(userRepo.findByUsername("jan")).thenReturn(Optional.empty());
        when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.register(req))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Brak roli ROLE_USER");
        verify(userRepo, never()).save(any());
    }

    @Test
    void login_shouldAuthenticateAndReturnToken() {
        LoginRequest req = new LoginRequest();
        req.setUsername("jan");
        req.setPassword("tajne");

        User user = new User();
        user.setUsername("jan");
        user.setPassword("zakodowane");
        user.setRoles(Collections.emptySet());
        when(userRepo.findByUsername("jan")).thenReturn(Optional.of(user));
        when(encoder.matches("tajne", "zakodowane")).thenReturn(true);
        when(jwtTokenProvider.generateToken("jan")).thenReturn("FAKE_JWT_TOKEN");

        String token = authService.login(req);

        assertThat(token).isEqualTo("FAKE_JWT_TOKEN");
    }
}
