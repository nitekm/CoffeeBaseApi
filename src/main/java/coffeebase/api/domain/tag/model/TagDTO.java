package coffeebase.api.domain.tag.model;

import coffeebase.api.domain.coffee.model.CoffeeDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TagDTO (
        Long id,
        String createdByUserId,
        @NotBlank String name,
        @NotBlank String color,
        List<CoffeeDTO>coffees
) {}
