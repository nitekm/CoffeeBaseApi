package coffeebase.api.aspect.accesscheck;

import coffeebase.api.domain.base.model.BaseEntity;
import coffeebase.api.utils.SecurityContextHelper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class SaveAspect {

    @Before("execution(* save*(..)) && args(entity)")
    public void beforeSave(BaseEntity entity) {
        entity.setCreatedByUserId(SecurityContextHelper.getUserFromSecurityContext().getUserId());
    }

    @Before("execution(* saveAll*(..)) && args(entities)")
    public void beforeSave(Collection<BaseEntity> entities) {
        entities.forEach(entity -> entity.setCreatedByUserId(
                SecurityContextHelper.getUserFromSecurityContext().getUserId())
        );
    }
}
