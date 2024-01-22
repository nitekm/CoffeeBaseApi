package coffeebase.api.domain.coffee.model;


import java.util.List;

public record PageCoffeeRequest(
        Integer pageSize,
        Integer pageNumber,
        String sortProperty,
        String sortDirection,
        Boolean favourite,
        String continent,
        String roastProfile
) {}
