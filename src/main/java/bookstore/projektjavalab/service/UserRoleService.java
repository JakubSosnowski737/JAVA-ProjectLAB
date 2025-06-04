package bookstore.projektjavalab.service;

import bookstore.projektjavalab.model.User;

public interface UserRoleService {
    User addRole(Long userId, String roleName);
    User removeRole(Long userId, String roleName);
}
