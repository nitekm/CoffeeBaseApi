package coffeebase.api.security.google;

import coffeebase.api.security.jwt.JwtTokenUtil;
import coffeebase.api.security.model.Token;
import coffeebase.api.security.model.User;
import coffeebase.api.security.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleTokenVerifier {

    private UserService userService;

    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.clientId}")
    private String clientID;

    public GoogleTokenVerifier(final UserService userService, final JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Token verifyGoogleToken(NetHttpTransport transport, GsonFactory jsonFactory, String idTokenString) throws GeneralSecurityException, IOException, IllegalAccessException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singleton(clientID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new IllegalAccessException("Invalid token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        User user = createUser(payload);

        final String token = jwtTokenUtil.generateToken(user);
        return new Token(token);
    }

    private User createUser(GoogleIdToken.Payload payload) {

        String userId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        return userService.checkUser(new User(userId, name, email));
    }
}
