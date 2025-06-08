package bookstore.projektjavalab.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    void shouldCreateBookAndSetFields() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Pan Tadeusz");
        book.setIsbn("1234567890");
        book.setDescription("Epopeja narodowa");
        book.setPrice(new BigDecimal("19.99"));

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Pan Tadeusz");
        assertThat(book.getIsbn()).isEqualTo("1234567890");
        assertThat(book.getDescription()).isEqualTo("Epopeja narodowa");
        assertThat(book.getPrice()).isEqualTo(new BigDecimal("19.99"));
    }

    @Test
    void shouldAddAuthorAndCategoryToBook() {
        Book book = new Book();
        Author author = new Author();
        author.setId(2L);
        Category category = new Category();
        category.setId(3L);

        book.getAuthors().add(author);
        book.getCategories().add(category);

        assertThat(book.getAuthors()).contains(author);
        assertThat(book.getCategories()).contains(category);
    }
}
