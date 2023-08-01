package coffeebase.api.utils;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.domain.tag.model.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static coffeebase.api.authentication.AuthenticationHelper.getUserId;

public class TestCoffeeUtils {
    public static Coffee createRandomCoffee(String name) {
        return Coffee.builder()
                .favourite(false)
                .name(name)
                .createdByUserId(getUserId())
                .build();
    }

    public static CoffeeDTO createCoffeeDTO(String name) {
        return new CoffeeDTO(
                null,
                getUserId(),
                name,
                null,
                null,
                null,
                null,
                null ,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new ArrayList<>());
    }

    public static CoffeeDTO createCoffeeDTOWithTags(String name) {
        final var tag1 = new TagDTO(null, getUserId(), "tag1", "color", null);
        final var tag2 = new TagDTO(null, getUserId(), "tag2", "color", null);
        final var tag3 = new TagDTO(null, getUserId(), "tag3", "color", null);
        final var tags = Arrays.asList(tag1, tag2, tag3);
        return new CoffeeDTO(
                null,
                getUserId(),
                name,
                null,
                null,
                null,
                null,
                null ,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                tags);
    }

    public static CoffeeDTO createCoffeeDTOWithTags(String name, List<TagDTO> tags) {
        return new CoffeeDTO(
                null,
                getUserId(),
                name,
                null,
                null,
                null,
                null,
                null ,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                tags);
    }
}
