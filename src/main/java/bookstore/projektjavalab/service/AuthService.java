package bookstore.projektjavalab.service;

import bookstore.projektjavalab.dto.AuthResponse;
import bookstore.projektjavalab.dto.LoginRequest;
import bookstore.projektjavalab.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
