// src/main/java/bookstore/projektjavalab/service/impl/CategoryServiceImpl.java
package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Category;
import bookstore.projektjavalab.repository.CategoryRepository;
import bookstore.projektjavalab.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Category create(Category category) {
        return repo.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = repo.findById(id).orElseThrow();
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repo.save(existing);
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
