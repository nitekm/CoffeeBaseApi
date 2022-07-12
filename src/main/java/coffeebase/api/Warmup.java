package coffeebase.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        //TODO: change to tags
//        logger.info("Application warmup after context refresh");
//        final String name = "v60";
//        if (!repository.existsByName(name)) {
//            logger.info("No required group found! Adding it!");
//            var group = new CoffeeGroup();
//            group.setName(name);
//            group.setGroupType(CoffeeGroup.GroupType.METHOD);
//            repository.save(group);
//        }
    }
}