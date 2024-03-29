package coffeebase.api.security.jwt;

import coffeebase.api.security.google.GoogleTokenVerifier;
import coffeebase.api.domain.user.model.User;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");


        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            logger.warn("JWT Token empty or does not begin with Bearer string, passing down filter chain");
            chain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = requestTokenHeader.substring(7);
            final User user = googleTokenVerifier.verifyGoogleToken(new NetHttpTransport(), GsonFactory.getDefaultInstance(), jwtToken);

            // if token is valid configure Spring Security to manually set
            // authentication
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, null);
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // After setting the Authentication in the context, we specify
            // that the current user is authenticated. So it passes the
            // Spring Security Configurations successfully.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        } catch (GeneralSecurityException | IllegalAccessException e) {
            logger.error("Access denied: " + e);
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/actuator");
    }
}
