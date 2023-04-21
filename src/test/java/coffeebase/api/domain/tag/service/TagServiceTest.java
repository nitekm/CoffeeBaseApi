package coffeebase.api.domain.tag.service;

import coffeebase.api.domain.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestTagUtils.createRandomTag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }

    @Test
    @DisplayName("Should return all tags")
    void givenTagsInDB_whenGetAll_thenReturnAllTags() {
        //given
        var tags = Arrays.asList(
                createRandomTag(),
                createRandomTag(),
                createRandomTag()
        );
        tagRepository.saveAll(tags);

        //when
        final int allTagsSize = tagService.getAll().size();

        //then
        assertEquals(3, allTagsSize);
    }

    @Test
    @DisplayName("Should find tags by name")
    void givenStringTag_whenSearchByName_returnAllTagsContainingThisString() {
        //given
        var tags = Arrays.asList(
                createRandomTag("tag1"),
                createRandomTag("tag2"),
                createRandomTag("tag3"),
                createRandomTag("randomName1"),
                createRandomTag("randomName2"),
                createRandomTag("randomName3")
        );
        tagRepository.saveAll(tags);

        //when
        final var searchResult = tagService.search("tag");

        //then
        assertEquals(3, searchResult.size());
    }

    @Test
    @DisplayName("Should return all tags on search by empty string")
    void givenEmptyString_whenSearchByName_returnAllTags() {
        //given
        var tags = Arrays.asList(
                createRandomTag("tag1"),
                createRandomTag("tag2"),
                createRandomTag("tag3"),
                createRandomTag("randomName1"),
                createRandomTag("randomName2"),
                createRandomTag("randomName3")
        );
        tagRepository.saveAll(tags);

        //when
        final var searchResult = tagService.search("");

        //then
        assertEquals(6, searchResult.size());
    }

    @Test
    @DisplayName("Should delete tag")
    void givenThreeTags_whenDeleteTag_thenTagIsDeleted() {
        //given
        var tags = Arrays.asList(
                createRandomTag(),
                createRandomTag(),
                createRandomTag()
        );
        tagRepository.saveAll(tags);

        //when
        tagService.deleteTag(1L);

        //then
        assertEquals(tags.size()-1, tagService.getAll().size());
    }

    @Test
    @DisplayName("Should throw exception when delete is called with non existing id")
    void givenNoExistingId_whenDeleteTag_thenThrowException() {
        //given
        var tags = Arrays.asList(
                createRandomTag(),
                createRandomTag(),
                createRandomTag()
        );
        tagRepository.saveAll(tags);

        //when
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> tagService.deleteTag(100L)
        );
    }
}
