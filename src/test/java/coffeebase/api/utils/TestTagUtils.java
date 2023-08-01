package coffeebase.api.utils;

import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.domain.tag.model.TagDTO;

import static coffeebase.api.authentication.AuthenticationHelper.getUserId;

public class TestTagUtils {
    public static Tag createRandomTag() {
        return Tag.builder()
                .name("tagName")
                .color("color")
                .createdByUserId(getUserId())
                .build();
    }

    public static Tag createRandomTag(String name) {
        return Tag.builder()
                .name(name)
                .color("color")
                .createdByUserId(getUserId())
                .build();
    }

    public static TagDTO createRandomTagDTO(String name) {
        return new TagDTO(
                null,
                getUserId(),
                name,
                "color",
                null
        );
    }
}
