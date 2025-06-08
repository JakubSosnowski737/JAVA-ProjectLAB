package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.security.JwtTokenProvider;
import bookstore.projektjavalab.service.UserRoleService;
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
import java.util.HashSet;
import java.util.Set;
import bookstore.projektjavalab.security.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AdminController.class,
        excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@Import(AdminControllerTest.DummySecurityConfig.class)
public class AdminControllerTest {

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
    @MockBean private UserRoleService userRoleService;

    @Test
    @DisplayName("POST /api/admin/users/{userId}/roles/{roleName} - dodanie roli użytkownikowi (success)")
    void addRole_success() throws Exception {
        User user = new User("testuser", "user@example.com", "password");
        user.setId(1L);
        Role adminRole = new Role("ROLE_ADMIN");
        adminRole.setId(2L);
        user.setRoles(new HashSet<>(Set.of(adminRole)));

        when(userRoleService.addRole(1L, "ROLE_ADMIN")).thenReturn(user);

        mockMvc.perform(post("/api/admin/users/1/roles/ROLE_ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.roles[0].name").value("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("DELETE /api/admin/users/{userId}/roles/{roleName} - usunięcie roli użytkownikowi (success)")
    void removeRole_success() throws Exception {
        User user = new User("testuser", "user@example.com", "password");
        user.setId(1L);
        user.setRoles(new HashSet<>());

        when(userRoleService.removeRole(1L, "ROLE_ADMIN")).thenReturn(user);

        mockMvc.perform(delete("/api/admin/users/1/roles/ROLE_ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.roles").isEmpty());
    }

    @Test
    @DisplayName("POST /api/admin/users/{userId}/roles/{roleName} - user nie istnieje (błąd)")
    void addRole_userNotFound() throws Exception {
        when(userRoleService.addRole(anyLong(), anyString())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(post("/api/admin/users/99/roles/ROLE_ADMIN"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    @Test
    @DisplayName("DELETE /api/admin/users/{userId}/roles/{roleName} - user nie istnieje (błąd)")
    void removeRole_userNotFound() throws Exception {
        when(userRoleService.removeRole(anyLong(), anyString())).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(delete("/api/admin/users/99/roles/ROLE_ADMIN"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    @Test
    @DisplayName("POST /api/admin/users/{userId}/roles/{roleName} - rola nie istnieje (błąd)")
    void addRole_roleNotFound() throws Exception {
        when(userRoleService.addRole(anyLong(), anyString())).thenThrow(new RuntimeException("Role not found"));

        mockMvc.perform(post("/api/admin/users/1/roles/ROLE_NOT_EXISTS"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Role not found"));
    }

    @Test
    @DisplayName("DELETE /api/admin/users/{userId}/roles/{roleName} - rola nie istnieje (błąd)")
    void removeRole_roleNotFound() throws Exception {
        when(userRoleService.removeRole(anyLong(), anyString())).thenThrow(new RuntimeException("Role not found"));

        mockMvc.perform(delete("/api/admin/users/1/roles/ROLE_NOT_EXISTS"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Role not found"));
    }
}
