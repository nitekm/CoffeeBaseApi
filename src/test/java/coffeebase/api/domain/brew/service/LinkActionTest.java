package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.model.BrewActionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkActionTest extends BrewActionTest {

    @Autowired
    private LinkAction linkAction;

    @Test
    @DisplayName("Should detach given brew from coffee")
    void testDetachSuccessful() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.LINK, coffee.getId(), brew.getId());

        //when
        linkAction.executeAction(brewAction);

        //then
        assertFalse(coffee.getBrews().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when coffee with given id is not found")
    void testCoffeeNotFound() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.LINK, 9999L, brew.getId());

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> linkAction.executeAction(brewAction));
    }

    @Test
    @DisplayName("Should throw exception when brew with given id is not found")
    void testBrewNotFound() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.LINK, coffee.getId(), 9999L);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> linkAction.executeAction(brewAction));
    }
}
