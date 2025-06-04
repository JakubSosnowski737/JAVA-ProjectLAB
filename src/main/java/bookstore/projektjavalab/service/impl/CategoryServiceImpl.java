package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Category;
import bookstore.projektjavalab.repository.CategoryRepository;
import bookstore.projektjavalab.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = findById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
