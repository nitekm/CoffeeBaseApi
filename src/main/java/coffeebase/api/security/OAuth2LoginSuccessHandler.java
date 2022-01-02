package coffeebase.api.security;

import coffeebase.api.coffee.CoffeeController;
import coffeebase.api.security.user.User;
import coffeebase.api.security.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

    private UserRepository userRepository;

    public OAuth2LoginSuccessHandler(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        CoffeebaseOAuth2User oAuth2User = (CoffeebaseOAuth2User) authentication.getPrincipal();
        if (!userRepository.existsByUsernameAndEmail(oAuth2User.getUsername(), oAuth2User.getEmail())) {
            log.info("No matching user found, saving new user \nUser {" + "\nusername: " + oAuth2User.getUsername() + "\nemail: " + oAuth2User.getEmail() + "\n}");
            userRepository.save(new User(oAuth2User.getUsername(), oAuth2User.getEmail()));
        }
        log.info("User logged in! \nUser {" + "\nusername: " + oAuth2User.getUsername() + "\nemail: " + oAuth2User.getEmail() + "\n}");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
