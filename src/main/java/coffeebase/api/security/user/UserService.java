package coffeebase.api.security.user;

import coffeebase.api.coffee.CoffeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        if (checkUserAndPassword(user)) {
            return user;
        } else {
            return null;
        }
    }

    public User register(User newUser) {
        if (userRepository.existsByUsernameAndEmail(newUser.getUsername(), newUser.getEmail())) {
            throw new IllegalArgumentException("User with given username or email already exists! Log in!");
        }
        //TODO: Add password encryption here as well
        return userRepository.save(new User(newUser.getUsername(), newUser.getPassword(), newUser.getEmail()));
    }

    private boolean checkUserAndPassword(User user) {
        var dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User with given username does not exist! Please register first"));
        //TODO Add encryption!!!
        if (user.getPassword().equals(dbUser.getPassword())) {
            log.info("Logging user \nUser {" + "\nusername: " + dbUser.getUsername() + "\nemail: " + dbUser.getEmail() + "\n}");
            return true;
        } else {
            log.info("Invalid password entered for user: \nUser {" + "\nusername: " + dbUser.getUsername() + "\nemail: " + dbUser.getEmail() + "\n}");
            throw new IllegalArgumentException("Username or password is not correct");
        }
    }
}
