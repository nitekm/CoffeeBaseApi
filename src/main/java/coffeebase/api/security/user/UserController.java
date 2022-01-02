package coffeebase.api.security.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(User user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("register")
    public ResponseEntity<User> register(User newUser) {
        return ResponseEntity.ok(userService.register(newUser));
    }
}
