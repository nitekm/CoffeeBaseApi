package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewActionDTO;
import coffeebase.api.domain.brew.model.BrewActionType;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import org.springframework.stereotype.Service;

@Service
public class DetachAction extends BrewAction {

    public DetachAction(BrewRepository brewRepository, CoffeeRepository coffeeRepository) {
        super(brewRepository, coffeeRepository);
    }

    @Override
    public Coffee doAction(Coffee coffee, Brew brew) {
        coffee.getBrews().remove(brew);
        return coffee;
    }

    @Override
    public boolean actionTypeMatch(BrewActionDTO brewActionDTO) {
        return brewActionDTO.brewActionType() == BrewActionType.DETACH;
    }
}
