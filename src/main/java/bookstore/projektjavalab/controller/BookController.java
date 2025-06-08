package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.Book;
import bookstore.projektjavalab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.ok(service.create(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Book>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> modify(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.update(id, book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
