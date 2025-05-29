// src/main/java/bookstore/projektjavalab/service/CategoryService.java
package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category update(Long id, Category category);
    Category findById(Long id);
    List<Category> findAll();
    void delete(Long id);
}
