// src/main/java/bookstore/projektjavalab/service/impl/AuthorServiceImpl.java
package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Author;
import bookstore.projektjavalab.repository.AuthorRepository;
import bookstore.projektjavalab.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repo;

    public AuthorServiceImpl(AuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public Author create(Author author) {
        return repo.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        Author existing = repo.findById(id).orElseThrow();
        existing.setName(author.getName());
        existing.setBiography(author.getBiography());
        return repo.save(existing);
    }

    @Override
    public Author findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public List<Author> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
