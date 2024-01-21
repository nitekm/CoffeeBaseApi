package coffeebase.api.domain.user;

import coffeebase.api.domain.user.service.UserSettingsService;
import coffeebase.api.exceptions.processing.ExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExceptionProcessing
@RequiredArgsConstructor
@RequestMapping("/user-settings")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    @DeleteMapping("delete-account")
    public ResponseEntity<Void> deleteUserAccount() {
        userSettingsService.deleteUserAccount();
        return ResponseEntity.noContent().build();
    }
}


