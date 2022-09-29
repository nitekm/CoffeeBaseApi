package coffeebase.api;

import coffeebase.api.domain.tag.TagRepository;
import coffeebase.api.domain.tag.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TagRepository tagRepository;

    public Warmup(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Application warmup after context refresh");
        //TODO: change colors
        final Tag tag1 = new Tag("V60", "blue");
        final Tag tag2 = new Tag("Sweet", "yellow");
        final Tag tag3 = new Tag("Daily", "red");
        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);

        tags.stream()
                .filter(tag -> !tagRepository.existsByName(tag.getName()))
                .forEach(tagRepository::save);
        logger.info("Initial Tag added!");
    }
}