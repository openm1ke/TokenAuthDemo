package tokenbased.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokenbased.models.User;
import tokenbased.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/private")
    public ResponseEntity<Map<String, String>> privatePage() {
        return ResponseEntity.ok().body(Map.of("message", "private page"));
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> authUser(@RequestParam String login, @RequestParam String password) {
        Optional<User> user = userService.authUser(login, password);
        if(user.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.get().getToken())
                    .body(Map.of("message", "User authenticated successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestParam String login, @RequestParam String password) {
        Optional<User> user = userService.registerUser(login, password);
        if(user.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "User already exists"));
        } else {
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.get().getToken())
                    .body(Map.of("message", "User created successfully"));
        }
    }

}
