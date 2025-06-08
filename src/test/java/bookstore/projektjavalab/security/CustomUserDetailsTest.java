package bookstore.projektjavalab.security;

import bookstore.projektjavalab.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private User user;
    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        userDetails = new CustomUserDetails(user);
    }

    @Test
    void getUsername_returnsCorrectValue() {
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void getPassword_returnsCorrectValue() {
        assertEquals("pass", userDetails.getPassword());
    }

    @Test
    void isAccountNonExpired_andOthers_shouldReturnTrue() {
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }
}
