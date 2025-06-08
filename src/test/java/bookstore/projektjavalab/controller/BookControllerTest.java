package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.Book;
import bookstore.projektjavalab.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // wyłącza security!
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
})
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    void createAndGetBook() throws Exception {
        Book book = new Book("Pan Tadeusz", "9781234567890", "Opis epopei", new BigDecimal("29.99"));

        String json = objectMapper.writeValueAsString(book);

        String response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Book saved = objectMapper.readValue(response, Book.class);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Pan Tadeusz");
        assertThat(saved.getIsbn()).isEqualTo("9781234567890");

        // pobranie po id
        mockMvc.perform(get("/api/books/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Pan Tadeusz"));
    }

    @Test
    void updateBook() throws Exception {
        Book book = repository.save(new Book("Stara nazwa", "9781111111111", "Opis", new BigDecimal("49.99")));

        Book updated = new Book("Nowa nazwa", "9781111111111", "Nowy opis", new BigDecimal("55.00"));
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/api/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Nowa nazwa"))
                .andExpect(jsonPath("$.price").value(55.00));
    }

    @Test
    void deleteBook() throws Exception {
        Book book = repository.save(new Book("Usuwana książka", "9782222222222", "Do usunięcia", new BigDecimal("12.99")));

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(book.getId())).isEmpty();
    }

    @Test
    void listBooks() throws Exception {
        repository.save(new Book("Książka 1", "9783333333333", "Opis 1", new BigDecimal("15.00")));
        repository.save(new Book("Książka 2", "9784444444444", "Opis 2", new BigDecimal("20.00")));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
