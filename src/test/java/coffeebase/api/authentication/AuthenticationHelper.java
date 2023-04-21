package coffeebase.api.authentication;

import coffeebase.api.security.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {
    private static User user;
    public static void setAuthenticationTestUser() {
        var testUser = createTestUser();
        var authentication = new UsernamePasswordAuthenticationToken(testUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static User createTestUser() {
        user = User.builder()
                .email("email")
                .username("testUser")
                .userId("123")
                .build();
        return user;
    }

    public static String getUserId() {
        return user.getUserId();
    }
}
