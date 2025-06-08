package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Book;
import bookstore.projektjavalab.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl service;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
    }

    @Test
    void create_ShouldReturnBook() {
        when(repository.save(book)).thenReturn(book);
        Book result = service.create(book);
        assertEquals(book, result);
    }

    @Test
    void findById_ShouldReturnBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        Book result = service.findById(1L);
        assertEquals(book, result);
    }

    @Test
    void findById_ShouldThrowIfNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void findAll_ShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(book));
        List<Book> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(any())).thenReturn(book);

        Book updated = new Book();
        updated.setTitle("Updated");
        Book result = service.update(1L, updated);
        assertEquals(book, result);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(1L);
    }
}
