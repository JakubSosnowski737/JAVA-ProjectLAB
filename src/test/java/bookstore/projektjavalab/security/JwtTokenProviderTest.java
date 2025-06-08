package bookstore.projektjavalab.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenProvider = new JwtTokenProvider();

        // Ustaw jwtSecret
        java.lang.reflect.Field secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtTokenProvider, "supersecrettestkeyjwtforunit1234"); // min. 32 znaki

        // Ustaw czas ważności tokena na 1 godzinę (w milisekundach)
        java.lang.reflect.Field expirationField = JwtTokenProvider.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtTokenProvider, 60 * 60 * 1000L);
    }

    @Test
    void generateToken_and_getUsernameFromJWT_ShouldReturnUsername() {
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);
        assertNotNull(token);

        String extracted = jwtTokenProvider.getUsernameFromJWT(token);
        assertEquals(username, extracted);
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        String invalidToken = "this.is.not.a.valid.jwt.token";
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
}
