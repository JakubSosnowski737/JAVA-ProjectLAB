package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.RoleRepository;
import bookstore.projektjavalab.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @InjectMocks
    private UserRoleServiceImpl service;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        role = new Role();
        role.setName("USER");
    }

    @Test
    void addRole_ShouldReturnUserWithRole() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepo.findByName("USER")).thenReturn(Optional.of(role));
        when(userRepo.save(user)).thenReturn(user);

        User result = service.addRole(1L, "USER");
        assertEquals(user, result);
    }

    @Test
    void addRole_ShouldThrowIfUserNotFound() {
        when(userRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.addRole(2L, "USER"));
    }

    @Test
    void addRole_ShouldThrowIfRoleNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepo.findByName("ADMIN")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.addRole(1L, "ADMIN"));
    }

    @Test
    void removeRole_ShouldReturnUserWithoutRole() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepo.findByName("USER")).thenReturn(Optional.of(role));
        when(userRepo.save(user)).thenReturn(user);

        User result = service.removeRole(1L, "USER");
        assertEquals(user, result);
    }
}
