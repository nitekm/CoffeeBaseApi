package coffeebase.api.domain.tag.model;

import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.security.model.User;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TagDTO(
        Integer id,
        @NotBlank String name,
        @NotBlank String color,
        List<CoffeeDTO>coffees,
        String userId,
        User user
) {}
