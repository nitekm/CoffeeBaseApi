package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.utils.TestBrewUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({BrewStepService.class})
public class BrewStepServiceTest {

    @Autowired
    private BrewStepService brewStepService;

    @MockBean
    private BrewRepository brewRepository;

    @MockBean
    private BrewMapper brewMapper;

    @Test
    @DisplayName("Should correctly calculate time from pour overs in seconds into total time in minutes on brew")
    void test_totalTimeSetCorrectly() {


        final var brew = TestBrewUtils.createExampleBrewWithRequiredFields();

    }

}
