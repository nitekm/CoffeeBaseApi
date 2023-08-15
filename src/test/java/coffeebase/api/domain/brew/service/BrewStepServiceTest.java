package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.utils.TestBrewUtils;
import lombok.AllArgsConstructor;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BrewStepServiceTest {

    @Autowired
    private  BrewStepService brewStepService;

    @Autowired
    private BrewRepository brewRepository;

    @Autowired
    private BrewMapper brewMapper;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
    }

    @Test
    @DisplayName("Should return new brew object when no brew found with it's ID")
    void givenNoBrewExists_whenInit_thenReturnNewBrew() {
        //given
        var brewDTO = createEmptyBrewDTO();

        //when
        BrewDTO initializedBrew = brewStepService.init(brewDTO);

        //then
        assertNotNull(initializedBrew);
    }
}