package bookstore.projektjavalab.security;

import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private CustomUserDetailsService service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("pass");
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

        var details = service.loadUserByUsername("testuser");
        assertEquals("testuser", details.getUsername());
        assertEquals("pass", details.getPassword());
    }

    @Test
    void loadUserByUsername_ShouldThrowIfNotFound() {
        when(userRepo.findByUsername("nouser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("nouser"));
    }
}
