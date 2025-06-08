package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.service.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRoleService userRoleService;

    public AdminController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/users/{userId}/roles/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addRole(@PathVariable Long userId, @PathVariable String roleName) {
        User updatedUser = userRoleService.addRole(userId, roleName);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}/roles/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> removeRole(@PathVariable Long userId, @PathVariable String roleName) {
        User updatedUser = userRoleService.removeRole(userId, roleName);
        return ResponseEntity.ok(updatedUser);
    }
}
