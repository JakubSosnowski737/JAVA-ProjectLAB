// src/main/java/bookstore/projektjavalab/security/CustomUserDetailsService.java
package bookstore.projektjavalab.security;

import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie znaleziony: " + username));
        return new CustomUserDetails(user);
    }
}
