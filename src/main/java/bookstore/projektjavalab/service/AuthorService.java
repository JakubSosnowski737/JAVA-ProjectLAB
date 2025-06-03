package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Author;
import java.util.List;

public interface AuthorService {
    Author create(Author author);
    Author findById(Long id);
    List<Author> findAll();
    Author update(Long id, Author author);
    void delete(Long id);
}
