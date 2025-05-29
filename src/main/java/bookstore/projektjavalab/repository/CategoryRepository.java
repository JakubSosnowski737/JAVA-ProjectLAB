// src/main/java/bookstore/projektjavalab/repository/CategoryRepository.java
package bookstore.projektjavalab.repository;

import bookstore.projektjavalab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
