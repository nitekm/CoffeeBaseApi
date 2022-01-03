package coffeebase.api.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: Test it
@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        return userRepository.save(new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail()));
    }

    private boolean checkUserAndPassword(User user) {
        var dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User with given username does not exist! Please register first"));
        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            log.info("Logging user \nUser {" + "\nusername: " + dbUser.getUsername() + "\nemail: " + dbUser.getEmail() + "\n}");
            return true;
        } else {
            log.info("Invalid password entered for user: \nUser {" + "\nusername: " + dbUser.getUsername() + "\nemail: " + dbUser.getEmail() + "\n}");
            throw new IllegalArgumentException("Username or password is not correct");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
