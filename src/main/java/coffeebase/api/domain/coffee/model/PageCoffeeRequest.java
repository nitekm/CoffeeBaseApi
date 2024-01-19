package coffeebase.api.domain.coffee.model;

public record PageCoffeeRequest(
        Integer pageSize,
        Integer pageNumber,
        String sortProperty,
        String sortDirection
) {}
