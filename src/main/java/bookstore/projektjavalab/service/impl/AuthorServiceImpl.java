package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Author;
import bookstore.projektjavalab.repository.AuthorRepository;
import bookstore.projektjavalab.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author create(Author author) {
        return repository.save(author);
    }

    @Override
    public Author findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public Author update(Long id, Author author) {
        Author existing = findById(id);
        existing.setName(author.getName());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
