package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.file.LocalCoffeeBaseFileService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.createCoffeeDTO;
import static coffeebase.api.utils.TestCoffeeUtils.createCoffeeDTOWithTags;
import static coffeebase.api.utils.TestCoffeeUtils.createRandomCoffee;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CoffeeServiceTest {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private LocalCoffeeBaseFileService localCoffeeBaseFileService;

    @Autowired
    private CoffeeMapper coffeeMapper;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }

    @Test
    @DisplayName("Should return all coffees when 3 coffees exists in database")
    void givenThreeCoffeesInDatabase_whenGetAllCoffees_thenReturnThreeCoffees() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        final int allCoffeesSize = coffeeService.getAllCoffees().size();

        //then
        assertEquals(3, allCoffeesSize);
    }

    @Test
    @DisplayName("Should return coffee with id 1 when getById with value 1 is called")
    void givenCoffeeWithId1_whenGetById1_thenReturnCoffeeWithId1() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        final var coffeeIdOne = coffeeService.getCoffeeById(1L);

        //then
        assertEquals(1L, coffeeIdOne.id());
    }

    @Test
    @DisplayName("Should save coffee in database with name given in DTO")
    void givenCoffeeDTOWithName_whenAddCoffee_thenReturnSavedCoffeeMappedToDTOWithName() {
        //given
        var coffeeDTO = createCoffeeDTO("testCoffee");

        //when
        final var savedMappedCoffee = coffeeService.addCoffee(coffeeDTO, null);

        //then
        assertAll(
                () -> assertNotEquals(null, savedMappedCoffee.id()),
                () -> assertEquals("testCoffee", savedMappedCoffee.name())
        );
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
    @DisplayName("Should update coffee")
    void givenUpdatedCoffee_whenEditCoffee_thenReturnUpdatedCoffee() {
        //given
        var coffee = createRandomCoffee("coffee");
        final var initialCoffee = coffeeRepository.save(coffee);
        var updatedCoffee = createCoffeeDTO("updatedName");

        //when
        final var updatedCoffeeDTO = coffeeService.updateCoffee(1L, updatedCoffee, null);

        //then
        assertAll(
                () -> assertEquals(initialCoffee.getId(), updatedCoffeeDTO.id()),
                () -> assertEquals("updatedName", updatedCoffeeDTO.name())
        );
    }

    @Test
    @DisplayName("Should throw exception when update is called with non existing id")
    void givenNoExistingId_whenUpdateCoffee_thenThrowException() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> coffeeService.updateCoffee(100L, null, null)
        );

    }

    @Test
    @DisplayName("Should delete coffee")
    void givenThreeCoffees_whenDeleteCoffee_getAllReturnTwoCoffees() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        coffeeService.deleteCoffee(1L);

        //then
        assertEquals(coffees.size()-1, coffeeService.getAllCoffees().size());
    }

    @Test
    @DisplayName("Should throw exception when delete is called with non existing id")
    void givenNoExistingId_whenDeleteCoffee_thenThrowException() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> coffeeService.deleteCoffee(100L)
        );
    }

    @Test
    @DisplayName("Should find coffee by name")
    void givenStringCoffee_whenSearch_returnAllCoffeesContainingThisString() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("coffee1"),
                createRandomCoffee("coffee2"),
                createRandomCoffee("coffee3"),
                createRandomCoffee("other1"),
                createRandomCoffee("other2"),
                createRandomCoffee("other3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        final var searchResult = coffeeService.search("coffee");

        //then
        assertEquals(3, searchResult.size());
    }

    @Test
    @DisplayName("Should return all coffees on search by empty string")
    void givenEmptyString_whenSearch_returnAllCoffees() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("coffee1"),
                createRandomCoffee("coffee2"),
                createRandomCoffee("coffee3"),
                createRandomCoffee("other1"),
                createRandomCoffee("other2"),
                createRandomCoffee("other3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        final var searchResult = coffeeService.search("");

        //then
        assertEquals(6, searchResult.size());
    }

    @Test
    @DisplayName("Should switch favourite field on coffee")
    void givenNonFavouriteCoffee_whenSwitchFavourite_thenReturnFavouriteCoffee() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        final var favouriteCoffee = coffeeService.switchFavourite(1L);

        //then
        assertEquals(true, favouriteCoffee.favourite());
    }

    @Test
    @DisplayName("Should throw exception when switch favourite is called with non existing id")
    void givenNoExistingId_whenSwitchFavourite_thenThrowException() {
        //given
        var coffees = Arrays.asList(
                createRandomCoffee("c1"),
                createRandomCoffee("c2"),
                createRandomCoffee("c3")
        );
        coffeeRepository.saveAll(coffees);

        //when
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> coffeeService.switchFavourite(100L)
        );
    }
}
