package coffeebase.api.aspect.accesscheck;

import coffeebase.api.domain.coffee.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static coffeebase.api.domain.base.model.error.ErrorMessage.COFFEE_NOT_FOUND;
import static coffeebase.api.domain.base.model.error.ErrorMessage.NOT_PERMITTED_TO_MODIFY;
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
                .orElseThrow(() -> new IllegalArgumentException(COFFEE_NOT_FOUND.getMessage()));

        if (!coffeeOwner.equalsIgnoreCase(requestingUserId)) {
            throw new IllegalAccessException(NOT_PERMITTED_TO_MODIFY.getMessage());
        }
    }
}
