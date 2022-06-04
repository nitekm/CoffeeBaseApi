package coffeebase.api.security.model;

public class Token {

    private String token;

    public Token() {

    }

    public Token(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
