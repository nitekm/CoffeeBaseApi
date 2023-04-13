package coffeebase.api.security.google;

import coffeebase.api.security.model.User;
import coffeebase.api.security.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class GoogleTokenVerifier {

    private final UserService userService;

    @Value("${clientId}")
    private String clientId;

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
        User user = User.builder()
                .userId(payload.getSubject())
                .email(payload.getEmail())
                .username((String) payload.get("name"))
                .build();

        return userService.findExistingOrSaveNew(user);
    }
}
