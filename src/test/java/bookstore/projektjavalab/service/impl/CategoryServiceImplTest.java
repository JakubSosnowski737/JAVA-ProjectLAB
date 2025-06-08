package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Category;
import bookstore.projektjavalab.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
    }

    @Test
    void create_ShouldReturnCategory() {
        when(repository.save(category)).thenReturn(category);
        Category result = service.create(category);
        assertEquals(category, result);
    }

    @Test
    void findById_ShouldReturnCategory() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        Category result = service.findById(1L);
        assertEquals(category, result);
    }

    @Test
    void findById_ShouldThrowIfNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void findAll_ShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(category));
        List<Category> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedCategory() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(repository.save(any())).thenReturn(category);

        Category updated = new Category();
        updated.setName("Updated");
        Category result = service.update(1L, updated);
        assertEquals(category, result);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(1L);
    }
}
