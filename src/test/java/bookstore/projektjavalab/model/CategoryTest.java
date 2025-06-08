package bookstore.projektjavalab.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void shouldCreateCategoryAndSetFields() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Poezja");

        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Poezja");
    }

    @Test
    void shouldAddBookToCategory() {
        Category category = new Category();
        Book book = new Book();
        book.setId(4L);

        category.getBooks().add(book);

        assertThat(category.getBooks()).contains(book);
    }
}
