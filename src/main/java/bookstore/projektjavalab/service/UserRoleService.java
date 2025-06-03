// src/main/java/bookstore/projektjavalab/service/UserRoleService.java
package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.User;
import java.util.List;

public interface UserRoleService {
    List<User> listAllUsers();
    User assignRole(Long userId, String roleName);
    User removeRole(Long userId, String roleName);
}
