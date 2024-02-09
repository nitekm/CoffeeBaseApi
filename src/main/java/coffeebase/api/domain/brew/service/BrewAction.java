package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewActionDTO;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import lombok.AllArgsConstructor;

import static coffeebase.api.domain.base.model.error.ErrorMessage.BREW_NOT_FOUND;
import static coffeebase.api.domain.base.model.error.ErrorMessage.COFFEE_NOT_FOUND;

@AllArgsConstructor
public abstract class BrewAction {

    private final BrewRepository brewRepository;
    private final CoffeeRepository coffeeRepository;

    public Coffee executeAction(BrewActionDTO brewActionDTO) {
           var brew = findBrew(brewActionDTO);
           var coffee = findCoffee(brewActionDTO);
           var preparedCoffee = doAction(coffee, brew);
           return coffeeRepository.save(preparedCoffee);
    }
    abstract Coffee doAction(Coffee coffee, Brew brew);
    abstract public boolean actionTypeMatch(BrewActionDTO brewActionDTO);

    private Brew findBrew(BrewActionDTO brewActionDTO) {
        return brewRepository.findById(brewActionDTO.brewId())
                .orElseThrow(() -> new IllegalArgumentException(BREW_NOT_FOUND.getMessage()));
    }

    private Coffee findCoffee(BrewActionDTO brewActionDTO) {
        return coffeeRepository.findById(brewActionDTO.coffeeId())
                .orElseThrow(() -> new IllegalArgumentException(COFFEE_NOT_FOUND.getMessage()));
    }
}
