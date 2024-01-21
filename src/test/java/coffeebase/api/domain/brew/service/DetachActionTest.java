package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.model.BrewActionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DetachActionTest extends BrewActionTest {

    @Autowired
    private DetachAction detachAction;

    @Test
    @DisplayName("Should detach given brew from coffee")
    void testDetachSuccessful() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.DETACH, coffee.getId(), brew.getId());

        //when
        detachAction.executeAction(brewAction);

        //then
        assertTrue(coffee.getBrews().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when coffee with given id is not found")
    void testCoffeeNotFound() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.DETACH, 9999L, brew.getId());

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> detachAction.executeAction(brewAction));
    }

    @Test
    @DisplayName("Should throw exception when brew with given id is not found")
    void testBrewNotFound() {
        //given
        var brew = createRandomBrew();
        var coffee = saveCoffeeWithBrew(brew);

        //and
        var brewAction = createDetachBrewActionDTO(BrewActionType.DETACH, coffee.getId(), 9999L);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> detachAction.executeAction(brewAction));
    }
}
