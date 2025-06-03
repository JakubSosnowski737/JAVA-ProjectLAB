package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Category;
import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category findById(Long id);
    List<Category> findAll();
    Category update(Long id, Category category);
    void delete(Long id);
}
