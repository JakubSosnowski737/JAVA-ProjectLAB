package bookstore.projektjavalab.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorTest {

    @Test
    void shouldCreateAuthorAndSetFields() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Adam Mickiewicz");
        author.setBiography("Polski poeta");

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Adam Mickiewicz");
        assertThat(author.getBiography()).isEqualTo("Polski poeta");
    }

    @Test
    void shouldAddBookToAuthor() {
        Author author = new Author();
        Book book = new Book();
        book.setId(1L);

        author.getBooks().add(book);

        assertThat(author.getBooks()).contains(book);
    }
}
