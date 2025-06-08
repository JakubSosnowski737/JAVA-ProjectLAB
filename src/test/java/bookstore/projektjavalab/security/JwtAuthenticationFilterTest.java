package bookstore.projektjavalab.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateRequestWithValidToken() throws ServletException, IOException {
        // given
        JwtTokenProvider tokenProvider = mock(JwtTokenProvider.class);
        CustomUserDetailsService userDetailsService = mock(CustomUserDetailsService.class);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        // Ustaw pola na mocki (bo nie masz wstrzykiwania przez konstruktor)
        setField(filter, "tokenProvider", tokenProvider);
        setField(filter, "customUserDetailsService", userDetailsService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String fakeJwt = "fake.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + fakeJwt);
        when(tokenProvider.validateToken(fakeJwt)).thenReturn(true);
        when(tokenProvider.getUsernameFromJWT(fakeJwt)).thenReturn("jan");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getAuthorities()).thenReturn(null);
        when(userDetailsService.loadUserByUsername("jan")).thenReturn(userDetails);

        filter.doFilterInternal(request, response, chain);

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(userDetails);

        verify(chain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateRequestWithNoToken() throws Exception {
        JwtTokenProvider tokenProvider = mock(JwtTokenProvider.class);
        CustomUserDetailsService userDetailsService = mock(CustomUserDetailsService.class);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        setField(filter, "tokenProvider", tokenProvider);
        setField(filter, "customUserDetailsService", userDetailsService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain).doFilter(request, response);
        verifyNoInteractions(tokenProvider, userDetailsService);
    }

    private static void setField(Object target, String field, Object value) {
        try {
            java.lang.reflect.Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
