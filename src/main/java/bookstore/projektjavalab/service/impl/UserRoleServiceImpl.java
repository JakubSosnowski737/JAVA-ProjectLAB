package bookstore.projektjavalab.service.impl;

import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.RoleRepository;
import bookstore.projektjavalab.repository.UserRepository;
import bookstore.projektjavalab.service.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User addRole(Long userId, String roleName) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));

        user.getRoles().add(role);
        return userRepo.save(user);
    }

    @Override
    public User removeRole(Long userId, String roleName) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));

        user.getRoles().remove(role);
        return userRepo.save(user);
    }
}
