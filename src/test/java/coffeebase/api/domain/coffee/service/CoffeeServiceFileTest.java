package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.file.CoffeeBaseFile;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.file.LocalCoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    void setup() throws IOException, NoSuchFieldException, IllegalAccessException {
        setAuthenticationTestUser();
        var root = LocalCoffeeBaseFileService.class.getDeclaredField("root");
        root.setAccessible(true);
        root.set(coffeeBaseFileService, Paths.get("test-dir").toAbsolutePath().normalize());
        Files.createDirectories(Paths.get("test-dir").toAbsolutePath().normalize());
        var file = CoffeeBaseFile.builder()
                .name("fileName")
                .path("path")
                .build();
    }

    @AfterEach
    void finish() throws IOException {
        var testDir = Paths.get("test-dir").toAbsolutePath().normalize();
        Files.walk(testDir)
                .sorted(java.util.Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
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
