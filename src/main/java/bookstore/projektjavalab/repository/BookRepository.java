package bookstore.projektjavalab.repository;

import bookstore.projektjavalab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
