package coffeebase.api.domain.brew.model;

import java.util.Set;

public record BrewDTO(
        Long id,
        String name,
        BrewMethod method,
        Integer waterAmountInMl,
        Integer waterTemp,
        Integer coffeeWeightInGrams,
        Integer grinderSetting,
        String filter,
        Set<PourOverDTO> pourOvers
)
{}
