package coffeebase.api.security.google;

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

    @Value("${clientId}")
    private String clientId;

    public GoogleTokenVerifier(final UserService userService) {
        this.userService = userService;
    }

    public User verifyGoogleToken(NetHttpTransport transport, GsonFactory jsonFactory, String idTokenString)
            throws GeneralSecurityException, IOException, IllegalAccessException {

        //build google token verifier
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singleton(clientId))
                .build();

        //verify token from request
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new IllegalAccessException("Invalid token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        //create User object from token payload
        return createUser(payload);
    }

    private User createUser(GoogleIdToken.Payload payload) {

        String userId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        return userService.findExistingOrSaveNew(new User(userId, name, email));
    }
}
