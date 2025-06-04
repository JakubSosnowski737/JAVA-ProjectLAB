package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Book;
import bookstore.projektjavalab.repository.BookRepository;
import bookstore.projektjavalab.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book update(Long id, Book book) {
        Book existing = findById(id);
        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setDescription(book.getDescription());
        existing.setPrice(book.getPrice());
        existing.setAuthors(book.getAuthors());
        existing.setCategories(book.getCategories());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
