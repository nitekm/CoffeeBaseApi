package coffeebase.api.domain.coffee.service;

import coffeebase.api.aspect.accesscheck.AccessCheckAspect;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.security.model.User;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CoffeeServiceAccessCheckTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private AccessCheckAspect accessCheckAspect;

    @Test
    @DisplayName("Should throw IllegalAccessException when requesting user is not user who created coffee")
    public void testCheckAccessUnauthorized() {
        //given
        Long coffeeId = 123L;
        var requestingUser = User.builder()
                .userId("user1")
                .build();
        String coffeeOwner = "coffeeCreator";
        var authentication = new UsernamePasswordAuthenticationToken(requestingUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(coffeeRepository.findCreatedByUserIdById(coffeeId)).thenReturn(Optional.of(coffeeOwner));

        // when then
        assertThrows(IllegalAccessException.class, () -> accessCheckAspect.checkAccess(mock(JoinPoint.class), coffeeId));
    }

    @Test
    @DisplayName("Should not throw any exceptions when requesting user is user who created coffee")
    public void testCheckAccessAuthorized() {
        //given
        Long coffeeId = 123L;
        var requestingUser = User.builder()
                .userId("coffeeCreator")
                .build();
        String coffeeOwner = "coffeeCreator";
        var authentication = new UsernamePasswordAuthenticationToken(requestingUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(coffeeRepository.findCreatedByUserIdById(coffeeId)).thenReturn(Optional.of(coffeeOwner));

        // when then
        assertDoesNotThrow(() -> accessCheckAspect.checkAccess(mock(JoinPoint.class), coffeeId));
    }
}
