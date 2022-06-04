package coffeebase.api.security.controller;

import coffeebase.api.security.google.GoogleTokenVerifier;
import coffeebase.api.security.model.Token;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController()
@RequestMapping("/authenticate")
public class SecurityController {

    private GoogleTokenVerifier tokenVerifier;

    public SecurityController(GoogleTokenVerifier tokenVerifier) {
        this.tokenVerifier = tokenVerifier;
    }

    @PostMapping
    ResponseEntity<Token> verifyToken(@RequestBody Token token)
            throws GeneralSecurityException, IOException, IllegalAccessException {
        Token response = tokenVerifier.verifyGoogleToken(new NetHttpTransport(), GsonFactory.getDefaultInstance(), token.getToken());
        System.out.println("response: " + response.getToken());
        return ResponseEntity.ok(response);
    }
}
