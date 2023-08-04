package coffeebase.api.domain.user;

import coffeebase.api.domain.user.model.SubscriptionDTO;
import coffeebase.api.domain.user.service.UserService;
import coffeebase.api.exceptions.processing.IllegalExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@IllegalExceptionProcessing
@RequestMapping("/subscription")
public class SubscriptionController {

    private final UserService userService;

    @PostMapping("save")
    public ResponseEntity<SubscriptionDTO> saveSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(userService.saveSubscription(subscriptionDTO));
    }
}
