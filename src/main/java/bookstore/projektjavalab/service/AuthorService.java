// src/main/java/bookstore/projektjavalab/service/AuthorService.java
package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);
    Author update(Long id, Author author);
    Author findById(Long id);
    List<Author> findAll();
    void delete(Long id);
}
