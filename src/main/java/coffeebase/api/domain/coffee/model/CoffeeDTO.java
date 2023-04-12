package coffeebase.api.domain.coffee.model;

import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.security.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CoffeeDTO(
        Integer id,
        @NotBlank(message = "Name cannot be empty") String name,
        @Min(value = 0) @Max(value = 6) Double rating,
        Boolean favourite,
        String coffeeImageName,
        String origin,
        String roaster,
        String processing,
        String roastProfile,
        String region,
        String continent,
        String farm,
        @Min(value = 0) @Max(value = 8849) Integer cropHeight,
        @Min(value = 0) @Max(value = 100) Integer scaRating,
        String userId,
        User user,
        List<TagDTO> tags

) {}
