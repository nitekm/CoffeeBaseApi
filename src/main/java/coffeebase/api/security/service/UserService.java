package coffeebase.api.security.service;

import coffeebase.api.security.model.User;
import coffeebase.api.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findExistingOrSaveNew(User user) {
        return userRepository.findByUserId(user.getUserId())
                .orElseGet(() -> userRepository.save(user));
    }
}
