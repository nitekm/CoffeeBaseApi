package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewActionDTO;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import lombok.AllArgsConstructor;

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
                .orElseThrow(() -> new IllegalArgumentException("Brew with id " + brewActionDTO.brewId() + "not found"));
    }

    private Coffee findCoffee(BrewActionDTO brewActionDTO) {
        return coffeeRepository.findById(brewActionDTO.coffeeId())
                .orElseThrow(() -> new IllegalArgumentException("Coffee with id " + brewActionDTO.coffeeId() + "not found"));
    }
}
