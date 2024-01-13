package coffeebase.api.domain.brew.model;

import coffeebase.api.domain.coffee.model.CoffeeDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

public record BrewDTO(
        Long id,
        String name,
        BrewMethod method,
        @Min(1) @Max(5000)
        Integer waterAmountInMl,
        @Min(1) @Max(100)
        Integer waterTemp,
        @Min(1) @Max(250)
        Integer coffeeWeightInGrams,
        @Min(1) @Max(100)
        Integer grinderSetting,
        String filter,
        String totalTime,
        BrewStatus status,
        List<PourOverDTO> pourOvers,
        List<CoffeeDTO> coffees
)
{}
