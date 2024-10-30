package tokenbased.services;

import org.springframework.stereotype.Service;
import tokenbased.models.User;
import tokenbased.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public boolean verifyToken(String token) {
        return userRepository.findByToken(token).isPresent();
    }

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }



}