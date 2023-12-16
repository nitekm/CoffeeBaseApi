package coffeebase.api.domain.brew.controller;

import coffeebase.api.domain.brew.service.BrewAction;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.coffee.service.CoffeeMapper;
import coffeebase.api.exceptions.processing.ExceptionProcessing;
import coffeebase.api.domain.brew.model.BrewActionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ExceptionProcessing
@RequestMapping("brews/execute-action")
@RequiredArgsConstructor
public class BrewActionController {

    private final List<BrewAction> brewActions;
    private final CoffeeMapper coffeeMapper;

    @PatchMapping
    public CoffeeDTO executeBrewAction(@RequestBody BrewActionDTO brewActionDTO) {
            return brewActions.stream()
                    .filter(brewAction -> brewAction.actionTypeMatch(brewActionDTO))
                    .findFirst()
                    .map(brewAction -> brewAction.executeAction(brewActionDTO))
                    .map(coffeeMapper::coffeeToDTO)
                    .orElseThrow(() -> new IllegalStateException("No matching startegy found to handle action" + brewActionDTO.brewActionType().name()));
    }
}
