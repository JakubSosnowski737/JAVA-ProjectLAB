package bookstore.projektjavalab.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    void shouldCreateRoleAndSetFields() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ADMIN");
    }
}
