// src/main/java/bookstore/projektjavalab/controller/AdminController.java
package bookstore.projektjavalab.controller;

import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.service.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRoleService roleService;

    public AdminController(UserRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(roleService.listAllUsers());
    }

    @PutMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<User> addRole(@PathVariable Long userId, @PathVariable String roleName) {
        return ResponseEntity.ok(roleService.assignRole(userId, roleName));
    }

    @DeleteMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<User> removeRole(@PathVariable Long userId, @PathVariable String roleName) {
        return ResponseEntity.ok(roleService.removeRole(userId, roleName));
    }
}
