package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;
import bookstore.projektjavalab.model.User;
import bookstore.projektjavalab.repository.UserRepository;
import bookstore.projektjavalab.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Niepoprawny login lub hasło"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Niepoprawny login lub hasło");
        }

        return jwtTokenProvider.generateToken(user.getUsername());
    }

    public ResponseEntity<?> register(RegisterRequest request) {
        return ResponseEntity.ok().body("Rejestracja zakończona powodzeniem");
    }
}
