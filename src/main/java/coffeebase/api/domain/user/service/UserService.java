package coffeebase.api.domain.user.service;

import coffeebase.api.domain.user.model.Subscription;
import coffeebase.api.domain.user.model.SubscriptionDTO;
import coffeebase.api.domain.user.model.User;
import coffeebase.api.domain.user.UserRepository;
import coffeebase.api.utils.SecurityContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    public User findExistingOrSaveNew(User user) {
        return userRepository.findByUserId(user.getUserId())
                .orElseGet(() -> userRepository.save(user));
    }

    public SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO) {
        final User userFromSecurityContext = SecurityContextHelper.getUserFromSecurityContext();
        return userRepository.findByUserId(userFromSecurityContext.getUserId())
                .map(user -> setSubscription(user, subscriptionDTO))
                .map(User::getSubscription)
                .map(subscriptionMapper::toDTO)
                .orElseThrow(() -> new IllegalStateException("No user found by userId from context"));
    }

    private User setSubscription(User user, SubscriptionDTO subscriptionDTO) {
        user.setSubscription(subscriptionMapper.toEntity(subscriptionDTO));
        return userRepository.save(user);
    }
}
