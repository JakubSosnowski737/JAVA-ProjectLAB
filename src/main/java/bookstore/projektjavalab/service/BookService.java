package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);
    Book update(Long id, Book book);
    Book findById(Long id);
    List<Book> findAll();
    void delete(Long id);
}
