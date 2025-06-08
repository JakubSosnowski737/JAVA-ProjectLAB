package bookstore.projektjavalab.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void shouldCreateUserAndSetFields() {
        User user = new User();
        user.setId(1L);
        user.setUsername("jan");
        user.setPassword("tajne");
        user.setEmail("jan@example.com");

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("jan");
        assertThat(user.getPassword()).isEqualTo("tajne");
        assertThat(user.getEmail()).isEqualTo("jan@example.com");
    }

    @Test
    void shouldAddRoleToUser() {
        User user = new User();
        Role role = new Role();
        role.setId(2L);
        role.setName("USER");

        user.getRoles().add(role);

        assertThat(user.getRoles()).contains(role);
    }
}
