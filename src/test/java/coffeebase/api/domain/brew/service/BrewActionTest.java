package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewActionDTO;
import coffeebase.api.domain.brew.model.BrewActionType;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BrewActionTest {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private BrewRepository brewRepository;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }

    protected BrewActionDTO createDetachBrewActionDTO(BrewActionType actionType, Long coffeeId, Long brewId) {
        return new BrewActionDTO(actionType, coffeeId, brewId);
    }

     protected Coffee saveCoffeeWithBrew(Brew brew) {
        List<Brew> brews = new ArrayList<>();
        brews.add(brew);
        var coffee = Coffee.builder()
                .name("Coffee")
                .brews(brews)
                .build();

        return coffeeRepository.save(coffee);
    }

    protected Coffee saveCoffeeWithoutBrew() {
        var coffee = Coffee.builder()
                .name("Coffee")
                .build();

        return coffeeRepository.save(coffee);
    }

    protected Brew createRandomBrew() {
        var brew = new Brew();
        brew.setName("random brew");
        return brewRepository.save(brew);
    }
}
