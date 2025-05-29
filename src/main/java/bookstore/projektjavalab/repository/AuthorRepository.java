// src/main/java/bookstore/projektjavalab/repository/AuthorRepository.java
package bookstore.projektjavalab.repository;

import bookstore.projektjavalab.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
