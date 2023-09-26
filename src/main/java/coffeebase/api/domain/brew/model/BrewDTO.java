package coffeebase.api.domain.brew.model;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record BrewDTO(
        Long id,
        String name,
        BrewMethod method,
        @Min(0) @Max(5000)
        Integer waterAmountInMl,
        @Min(0) @Max(100)
        Integer waterTemp,
        @Min(0) @Max(250)
        Integer coffeeWeightInGrams,
        @Min(0) @Max(100)
        Integer grinderSetting,
        String filter,
        @Min(0) @Max(100)
        Integer totalTime,
        List<PourOverDTO> pourOvers,
        List<CoffeeDTO> coffees
)
{}
