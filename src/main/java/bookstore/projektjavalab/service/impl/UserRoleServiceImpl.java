// src/main/java/bookstore/projektjavalab/service/impl/UserRoleServiceImpl.java
package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.RoleRepository;
import bookstore.projektjavalab.repository.UserRepository;
import bookstore.projektjavalab.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserRoleServiceImpl(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User assignRole(Long userId, String roleName) {
        User user = userRepo.findById(userId).orElseThrow();
        Role role = roleRepo.findByName(roleName).orElseThrow();
        user.getRoles().add(role);
        return userRepo.save(user);
    }

    @Override
    public User removeRole(Long userId, String roleName) {
        User user = userRepo.findById(userId).orElseThrow();
        Role role = roleRepo.findByName(roleName).orElseThrow();
        user.getRoles().remove(role);
        return userRepo.save(user);
    }
}
