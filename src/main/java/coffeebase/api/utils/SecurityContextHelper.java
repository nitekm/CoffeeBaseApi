package coffeebase.api.utils;

import coffeebase.api.security.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {
    public static User getUserFromSecurityContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
