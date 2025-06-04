package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;
import bookstore.projektjavalab.model.Role;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.RoleRepository;
import bookstore.projektjavalab.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepo,
                       RoleRepository roleRepo,
                       PasswordEncoder encoder,
                       AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.authManager = authManager;
    }

    public void register(RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Użytkownik już istnieje");
        }
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Brak roli ROLE_USER"));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.getRoles().add(userRole);
        userRepo.save(user);
    }

    public String login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        // tutaj można generować JWT; na potrzeby przykładu zwrócimy placeholder:
        return "TOKEN_JWT_POLOGOWANIU";
    }
}
