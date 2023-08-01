package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import coffeebase.api.utils.TestTagUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.*;
import static coffeebase.api.utils.TestTagUtils.createRandomTag;
import static coffeebase.api.utils.TestTagUtils.createRandomTagDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CoffeeServiceTagsTest {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeBaseFileService coffeeBaseFileService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CoffeeMapper coffeeMapper;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }


    @Test
    @DisplayName("Should save coffee in database with tags")
    void givenCoffeeDTOWithTags_whenAddCoffee_thenReturnSavedCoffeeMappedToDTOWithTags() {
        //given
        var coffeeDTO = createCoffeeDTOWithTags("testCoffee");

        //when
        final var savedMappedCoffee = coffeeService.addCoffee(coffeeDTO, null);

        //then
        assertNotNull(savedMappedCoffee.tags());
    }

    @Test
    @DisplayName("Should link coffee with existing tags when tags on coffee already exists in DB")
    public void givenCoffeeWithTags_whenAddCoffee_thenLinkCoffeeWithTagsExistingInDatabase() {
        //given
        final var tag1 = createRandomTag("tag1");
        final var tag2 = createRandomTag("tag2");
        final var tag3 = createRandomTag("tag3");
        final var tag4 = createRandomTag("tag4");
        var tags = Arrays.asList(tag1, tag2, tag3, tag4);
        tagRepository.saveAll(tags);

        final var coffee1 = createRandomCoffee("coffee1");
        coffee1.setTags(tags);

        //when
        coffeeRepository.save(coffee1);

        //then
        assertEquals(4, tagRepository.findAll().size());

    }

    @Test
    @DisplayName("Should save tags provided in updated coffee")
    void givenCoffeeWithNoTags_whenUpdateCoffee_thenReturnCoffeeWithTags() {
        //given
        var testCoffeeDTO = createCoffeeDTO("testCoffee");
        var savedCoffee = coffeeService.addCoffee(testCoffeeDTO, null);

        var updatedCoffeeDTO = createCoffeeDTOWithTags("updatedCoffee");

        //when
        var updatedCoffee = coffeeService.updateCoffee(savedCoffee.id(), updatedCoffeeDTO, null);

        //then
        assertTrue(!updatedCoffee.tags().isEmpty());
    }

    @Test
    @DisplayName("Should replace tags on coffee with provided in updated coffee")
    void givenCoffeeWithTags_whenUpdateCoffee_thenReturnCoffeeWithNewTags() {
        //given
        var testCoffeeDTO = createCoffeeDTOWithTags("testCoffee");
        var savedCoffee = coffeeService.addCoffee(testCoffeeDTO, null);

        var updatedCoffeeDTO = createCoffeeDTOWithTags("updatedCoffee", Arrays.asList(
                createRandomTagDTO("newTag1"),
                createRandomTagDTO("newTag2"),
                createRandomTagDTO("newTag3")
        ));

        //when
        var updatedCoffee = coffeeService.updateCoffee(savedCoffee.id(), updatedCoffeeDTO, null);

        //then
        assertEquals(updatedCoffeeDTO.tags().size(), updatedCoffee.tags().size());
    }

    @Test
    @DisplayName("Should remove tags when no tags provided in updated coffee")
    void givenCoffeeWithTags_whenUpdateCoffeeWithNoTags_thenReturnCoffeeWithNoTags() {
        //given
        var testCoffeeDTO = createCoffeeDTOWithTags("testCoffee");
        var savedCoffee = coffeeService.addCoffee(testCoffeeDTO, null);

        var updatedCoffeeDTO = createCoffeeDTO("updatedCoffee");

        //when
        var updatedCoffee = coffeeService.updateCoffee(savedCoffee.id(), updatedCoffeeDTO, null);

        //then
        assertTrue(updatedCoffee.tags().isEmpty());
    }
}
