//package bookstore.projektjavalab.controller;
//
//import bookstore.projektjavalab.model.Category;
//import bookstore.projektjavalab.security.JwtTokenProvider;
//import bookstore.projektjavalab.service.CategoryService;
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
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import bookstore.projektjavalab.security.CustomUserDetailsService;
//
//@WebMvcTest(controllers = CategoryController.class)
//@Import(CategoryControllerTest.DummySecurityConfig.class)
//public class CategoryControllerTest {
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
//    @MockBean private CategoryService categoryService;
//
//    @Test
//    @DisplayName("POST /api/categories - Utworzenie kategorii (success)")
//    void createCategory_success() throws Exception {
//        Category input = new Category("Fantasy", "Książki fantasy");
//        input.setBooks(new HashSet<>());
//
//        Category saved = new Category("Fantasy", "Książki fantasy");
//        saved.setId(1L);
//        saved.setBooks(new HashSet<>());
//
//        when(categoryService.create(any(Category.class))).thenReturn(saved);
//
//        mockMvc.perform(post("/api/categories")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Fantasy"))
//                .andExpect(jsonPath("$.description").value("Książki fantasy"));
//    }
//
//    @Test
//    @DisplayName("GET /api/categories/{id} - Pobierz kategorię (success)")
//    void getCategoryById_success() throws Exception {
//        Category category = new Category("Horror", "Straszne książki");
//        category.setId(2L);
//        category.setBooks(new HashSet<>());
//
//        when(categoryService.findById(2L)).thenReturn(category);
//
//        mockMvc.perform(get("/api/categories/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2L))
//                .andExpect(jsonPath("$.name").value("Horror"))
//                .andExpect(jsonPath("$.description").value("Straszne książki"));
//    }
//
//    @Test
//    @DisplayName("GET /api/categories/{id} - Kategoria nie istnieje (not found)")
//    void getCategoryById_notFound() throws Exception {
//        when(categoryService.findById(99L)).thenThrow(new RuntimeException("Category not found"));
//
//        mockMvc.perform(get("/api/categories/99"))
//                .andExpect(status().isInternalServerError());
//    }
//
//    @Test
//    @DisplayName("GET /api/categories - Lista kategorii (gdy są dane)")
//    void listCategories_success() throws Exception {
//        Category c1 = new Category("Fantasy", "Książki fantasy");
//        c1.setId(1L); c1.setBooks(new HashSet<>());
//        Category c2 = new Category("Horror", "Straszne książki");
//        c2.setId(2L); c2.setBooks(new HashSet<>());
//
//        when(categoryService.findAll()).thenReturn(Arrays.asList(c1, c2));
//
//        mockMvc.perform(get("/api/categories"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    @DisplayName("GET /api/categories - Lista kategorii (pusta)")
//    void listCategories_empty() throws Exception {
//        when(categoryService.findAll()).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/categories"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//    }
//
//    @Test
//    @DisplayName("PUT /api/categories/{id} - Modyfikacja kategorii (success)")
//    void updateCategory_success() throws Exception {
//        Category input = new Category("Przygodowa", "Książki przygodowe");
//        input.setBooks(new HashSet<>());
//        Category updated = new Category("Przygodowa", "Książki przygodowe");
//        updated.setId(1L); updated.setBooks(new HashSet<>());
//
//        when(categoryService.update(eq(1L), any(Category.class))).thenReturn(updated);
//
//        mockMvc.perform(put("/api/categories/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Przygodowa"))
//                .andExpect(jsonPath("$.description").value("Książki przygodowe"));
//    }
//
//    @Test
//    @DisplayName("PUT /api/categories/{id} - Modyfikacja nieistniejącej kategorii")
//    void updateCategory_notFound() throws Exception {
//        Category input = new Category("Nieistniejąca", "Brak");
//        input.setBooks(new HashSet<>());
//
//        when(categoryService.update(eq(99L), any(Category.class))).thenThrow(new RuntimeException("Category not found"));
//
//        mockMvc.perform(put("/api/categories/99")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isInternalServerError());
//    }
//
//    @Test
//    @DisplayName("DELETE /api/categories/{id} - Usuwanie kategorii (success)")
//    void deleteCategory_success() throws Exception {
//        Mockito.doNothing().when(categoryService).delete(1L);
//
//        mockMvc.perform(delete("/api/categories/1"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("DELETE /api/categories/{id} - Usuwanie nieistniejącej kategorii")
//    void deleteCategory_notFound() throws Exception {
//        Mockito.doThrow(new RuntimeException("Category not found")).when(categoryService).delete(99L);
//
//        mockMvc.perform(delete("/api/categories/99"))
//                .andExpect(status().isInternalServerError());
//    }
//}
