package coffeebase.api.aspect.accesscheck;

import coffeebase.api.domain.coffee.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static coffeebase.api.utils.SecurityContextHelper.getUserFromSecurityContext;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessCheckAspect {

    private final CoffeeRepository coffeeRepository;

    @Before("@annotation(AccessCheck) && args(id,..)")
    public void checkAccess(JoinPoint joinPoint, Long id) throws IllegalAccessException {
        final var requestingUserId = getUserFromSecurityContext().getUserId();
        final var coffeeOwner = coffeeRepository.findCreatedByUserIdById(id)
                .orElseThrow(IllegalArgumentException::new);

        if (!coffeeOwner.equalsIgnoreCase(requestingUserId)) {
            throw new IllegalAccessException();
        }
    }
}
