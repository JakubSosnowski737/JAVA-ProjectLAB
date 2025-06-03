package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.Book;
import java.util.List;

public interface BookService {
    Book create(Book book);
    Book findById(Long id);
    List<Book> findAll();
    Book update(Long id, Book book);
    void delete(Long id);
}
