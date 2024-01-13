package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.BrewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestBrewUtils.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BrewStepServiceIntegrationTest {

    @Autowired
    private BrewStepService brewStepService;
    @Autowired
    private BrewRepository brewRepository;
    @Autowired
    private BrewMapper brewMapper;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }

    @Test
    @DisplayName("Should return new brew object when DTO has id null")
    void StepGeneral_init() {
        //given
        var brewDTO = createEmptyBrewDTO();

        //when
        BrewDTO initializedBrew = brewStepService.init(brewDTO);

        //then
        assertNotNull(initializedBrew);
    }

    @Test
    @DisplayName("Should throw exception when no brew found with ID in DTO")
    void StepGeneral_init_exception() {
        //given
        var brewDTO = createBrewDTOWthId();

        //when
        //then
        assertThrows(
                IllegalArgumentException.class,
                () -> brewStepService.init(brewDTO)
        );
    }

    @Test
    @DisplayName("Should save brew with general info on finish")
    void StepGeneral_finish() {
        //given
        //init was done
        var brewId = executeInitAndGetBrewId();

        //and
        var brewGeneralInfo = createBrewDTOWithGeneralInfo(brewId);


        //when
        BrewDTO finishStepGeneralInfo = brewStepService.finish(brewGeneralInfo);

        //then
        assertAll(
                () -> assertNotNull(finishStepGeneralInfo.name()),
                () -> assertNotNull(finishStepGeneralInfo.method())
        );
    }

    @Test
    @DisplayName("Should save brew with ingredients on finish")
    void StepIngredients_finish() {
        //given
        //init was done
        var brewId = executeInitAndGetBrewId();

        //and
        var brewIngredients = createBrewDTOWithIngredients(brewId);


        //when
        BrewDTO finishStepIngredients = brewStepService.finish(brewIngredients);

        //then
        assertAll(
                () -> assertNotNull(finishStepIngredients.waterTemp()),
                () -> assertNotNull(finishStepIngredients.waterAmountInMl()),
                () -> assertNotNull(finishStepIngredients.grinderSetting()),
                () -> assertNotNull(finishStepIngredients.coffeeWeightInGrams()),
                () -> assertNotNull(finishStepIngredients.filter())
        );
    }

    @Test
    @DisplayName("Should save brew with pourOvers on finish")
    void StepPourOvers_finish() {
        //given
        //init was done
        var brewId = executeInitAndGetBrewId();

        //and
        var brewPourOvers = createBrewDTOWithPourOvers(brewId);


        //when
        var finishStepPourOvers = brewStepService.finish(brewPourOvers);

        //then
        assertNotEmpty(finishStepPourOvers.pourOvers(), "PourOvers not empty");
    }

    private Long executeInitAndGetBrewId() {
        return brewStepService.init(createEmptyBrewDTO()).id();
    }
}
