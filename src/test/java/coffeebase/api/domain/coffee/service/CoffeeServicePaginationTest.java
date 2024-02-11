package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.createRandomCoffee;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CoffeeServicePaginationTest {

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
}
