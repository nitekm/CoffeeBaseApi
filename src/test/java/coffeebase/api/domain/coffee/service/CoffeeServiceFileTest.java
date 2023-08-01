package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CoffeeServiceFileTest {

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
    @DisplayName("Should save coffee with image when image is present")
    void givenCoffeeWithImage_whenAddCoffee_thenReturnSavedCoffeeWithImage() {
        //given
        var coffeeDTO = createCoffeeDTO("withImage");
        var image = new MockMultipartFile("fileName", "file".getBytes());

        //when
        final var savedMappedCoffee = coffeeService.addCoffee(coffeeDTO, image);

        //then
        assertNotNull(savedMappedCoffee.coffeeImageName());
    }

    @Test
    @DisplayName("Should update coffee with new image when it's provided")
    void givenCoffeeUpdateWithImage_whenUpdateCoffee_thenReturnSavedCoffeeWithUpdatedImage() {
        //given
        var coffeeWithImage = saveCoffeeWithImage("initialImage");
        var updatedCoffee = createCoffeeDTO("withNewImage");
        var updatedImage = new MockMultipartFile("updateImage", "updateImage", null, "file".getBytes());

        //when
         final var updatedMappedCoffee = coffeeService.updateCoffee(coffeeWithImage.id(), updatedCoffee, updatedImage);

        //then
        assertThat(updatedMappedCoffee.coffeeImageName(), containsString("updateImage"));
    }

    //existing coffee with image - update with no image
    @Test
    @DisplayName("Should add image to coffee when coffee has no image")
    void givenCoffeeWithNoImageAndUpdateWithImage_whenUpdateCoffee_thenReturnSavedCoffeeWithUpdatedImage() {
        //given
        var coffeeDTONoImage = createCoffeeDTO("noImageCoffee");
        var savedCoffeeNoImage = coffeeService.addCoffee(coffeeDTONoImage, null);
        var updatedCoffee = createCoffeeDTO("withNewImage");
        var updatedImage = new MockMultipartFile("addImage", "addImage", null, "file".getBytes());

        //when
        final var updatedMappedCoffee = coffeeService.updateCoffee(savedCoffeeNoImage.id(), updatedCoffee, updatedImage);

        //then
        assertThat(updatedMappedCoffee.coffeeImageName(), containsString("addImage"));
    }

    private CoffeeDTO saveCoffeeWithImage(String imageName) {
        var coffeeDTO = createCoffeeDTO("withImage");
        var image = new MockMultipartFile(imageName, "file".getBytes());
        return coffeeService.addCoffee(coffeeDTO, image);
    }
}
