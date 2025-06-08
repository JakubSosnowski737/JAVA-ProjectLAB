//package bookstore.projektjavalab.controller;
//
//import bookstore.projektjavalab.model.User;
//import bookstore.projektjavalab.security.JwtTokenProvider;
//import bookstore.projektjavalab.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.math.BigDecimal;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import bookstore.projektjavalab.security.CustomUserDetailsService;
//
//@WebMvcTest(controllers = UserController.class)
//@Import(UserControllerTest.DummySecurityConfig.class)
//public class UserControllerTest {
//    @TestConfiguration
//    static class DummySecurityConfig {
//        @Bean
//        public JwtTokenProvider jwtTokenProvider() {
//            return new JwtTokenProvider();
//        }
//        @Bean
//        public CustomUserDetailsService customUserDetailsService() {
//            return Mockito.mock(CustomUserDetailsService.class);
//        }
//    }
//
//    @Autowired private MockMvc mockMvc;
//    @Autowired private ObjectMapper objectMapper;
//
//    @MockBean private UserService userService;
//
//    @Test
//    @DisplayName("POST /api/users - utwórz użytkownika (success)")
//    void createUser_success() throws Exception {
//        User input = new User("user123", "user@example.com", "password123");
//        User saved = new User("user123", "user@example.com", "password123");
//        saved.setId(1L);
//        saved.setRoles(new HashSet<>());
//
//        when(userService.saveUser(any(User.class))).thenReturn(saved);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("user123"))
//                .andExpect(jsonPath("$.email").value("user@example.com"));
//    }
//
//    @Test
//    @DisplayName("GET /api/users/{id} - pobierz użytkownika (success)")
//    void getUserById_success() throws Exception {
//        User user = new User("admin", "admin@example.com", "adminpass");
//        user.setId(2L);
//        user.setRoles(new HashSet<>());
//
//        when(userService.getUserById(2L)).thenReturn(user);
//
//        mockMvc.perform(get("/api/users/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2L))
//                .andExpect(jsonPath("$.username").value("admin"))
//                .andExpect(jsonPath("$.email").value("admin@example.com"));
//    }
//
//    @Test
//    @DisplayName("GET /api/users/{id} - użytkownik nie istnieje (not found)")
//    void getUserById_notFound() throws Exception {
//        when(userService.getUserById(99L)).thenThrow(new RuntimeException("User not found"));
//
//        mockMvc.perform(get("/api/users/99"))
//                .andExpect(status().isInternalServerError());
//        // Zamień na isNotFound() jeśli masz własną obsługę błędów
//    }
//
//    @Test
//    @DisplayName("GET /api/users - lista użytkowników (gdy są dane)")
//    void listUsers_success() throws Exception {
//        User u1 = new User("user1", "u1@example.com", "pass1");
//        u1.setId(1L); u1.setRoles(new HashSet<>());
//        User u2 = new User("user2", "u2@example.com", "pass2");
//        u2.setId(2L); u2.setRoles(new HashSet<>());
//
//        when(userService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    @DisplayName("GET /api/users - lista użytkowników (pusta)")
//    void listUsers_empty() throws Exception {
//        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//    }
//
//    @Test
//    @DisplayName("PUT /api/users/{id} - modyfikacja użytkownika (success)")
//    void updateUser_success() throws Exception {
//        User input = new User("newname", "newmail@example.com", "newpass");
//        User updated = new User("newname", "newmail@example.com", "newpass");
//        updated.setId(1L); updated.setRoles(new HashSet<>());
//
//        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);
//
//        mockMvc.perform(put("/api/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("newname"))
//                .andExpect(jsonPath("$.email").value("newmail@example.com"));
//    }
//
//    @Test
//    @DisplayName("PUT /api/users/{id} - modyfikacja nieistniejącego użytkownika")
//    void updateUser_notFound() throws Exception {
//        User input = new User("fail", "fail@example.com", "fail");
//        when(userService.updateUser(eq(99L), any(User.class))).thenThrow(new RuntimeException("User not found"));
//
//        mockMvc.perform(put("/api/users/99")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isInternalServerError());
//    }
//
//    @Test
//    @DisplayName("DELETE /api/users/{id} - usuń użytkownika (success)")
//    void deleteUser_success() throws Exception {
//        Mockito.doNothing().when(userService).deleteUser(1L);
//
//        mockMvc.perform(delete("/api/users/1"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("DELETE /api/users/{id} - usuń nieistniejącego użytkownika")
//    void deleteUser_notFound() throws Exception {
//        Mockito.doThrow(new RuntimeException("User not found")).when(userService).deleteUser(99L);
//
//        mockMvc.perform(delete("/api/users/99"))
//                .andExpect(status().isInternalServerError());
//    }
//}
