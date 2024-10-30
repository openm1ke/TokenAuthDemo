package tokenbased.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tokenbased.models.User;
import tokenbased.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }


    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> registerUser(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        if(user.isPresent()) {
            return Optional.empty();
        }
        User createdUser = createUser(login, password);
        return Optional.of(createdUser);
    }

    public Optional<User> authUser(String login, String password) {
        Optional<User> user = userRepository.findByLoginAndPassword(login, password);
        if(user.isPresent()) {
            user.get().setToken(authService.generateToken());
            userRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setToken(authService.generateToken());
        return userRepository.save(user);
    }

}
