package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Book;
import bookstore.projektjavalab.repository.BookRepository;
import bookstore.projektjavalab.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public Book create(Book book) {
        return repo.save(book);
    }

    @Override
    public Book update(Long id, Book book) {
        Book existing = repo.findById(id).orElseThrow();
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setIsbn(book.getIsbn());
        existing.setDescription(book.getDescription());
        existing.setPrice(book.getPrice());
        return repo.save(existing);
    }

    @Override
    public Book findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public List<Book> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
