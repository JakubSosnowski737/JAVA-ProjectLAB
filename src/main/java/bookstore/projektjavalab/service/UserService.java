
package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.UserResponseDto;
import bookstore.projektjavalab.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    List<UserResponseDto> findAllAsDto();
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User user);
    User createUser(User user);
    void deleteUser(Long id);
}
