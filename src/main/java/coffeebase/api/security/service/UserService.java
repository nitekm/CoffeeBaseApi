package coffeebase.api.security.service;

import coffeebase.api.security.model.User;
import coffeebase.api.security.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User checkUser(User user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            return userRepository.findByUserId(user.getUserId());
        }
        else {
            return userRepository.save(user);
        }
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
