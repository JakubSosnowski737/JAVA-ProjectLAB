package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.Author;
import bookstore.projektjavalab.repository.AuthorRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // WYŁĄCZA SPRING SECURITY!
@AutoConfigureTestDatabase // używa H2
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
})
class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    void createAndGetAuthor() throws Exception {
        Author author = new Author("Adam", "Bio");

        // Tworzenie
        String json = objectMapper.writeValueAsString(author);

        String response = mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Author saved = objectMapper.readValue(response, Author.class);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Adam");

        // Pobranie po ID
        mockMvc.perform(get("/api/authors/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Adam"));
    }

    @Test
    void updateAuthor() throws Exception {
        Author author = repository.save(new Author("Adam", "Bio"));

        Author updated = new Author("Ewa", "NewBio");
        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/api/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ewa"));
    }

    @Test
    void deleteAuthor() throws Exception {
        Author author = repository.save(new Author("Adam", "Bio"));

        mockMvc.perform(delete("/api/authors/" + author.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(author.getId())).isEmpty();
    }

    @Test
    void listAuthors() throws Exception {
        repository.save(new Author("Adam", "Bio"));
        repository.save(new Author("Ewa", "Bio"));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
