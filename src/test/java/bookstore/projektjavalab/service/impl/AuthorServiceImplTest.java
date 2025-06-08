package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Author;
import bookstore.projektjavalab.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
    }

    @Test
    void create_ShouldReturnAuthor() {
        when(repository.save(author)).thenReturn(author);
        Author result = service.create(author);
        assertEquals(author, result);
    }

    @Test
    void findById_ShouldReturnAuthor() {
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        Author result = service.findById(1L);
        assertEquals(author, result);
    }

    @Test
    void findById_ShouldThrowIfNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void findAll_ShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(author));
        List<Author> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedAuthor() {
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(repository.save(any())).thenReturn(author);

        Author updated = new Author();
        updated.setName("Updated");
        Author result = service.update(1L, updated);
        assertEquals(author, result);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(1L);
    }
}
