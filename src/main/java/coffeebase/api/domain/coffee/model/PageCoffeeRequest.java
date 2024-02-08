package coffeebase.api.domain.coffee.model;


import java.util.List;
import java.util.Map;
import java.util.Set;

public record PageCoffeeRequest(
        Integer pageSize,
        Integer pageNumber,
        String sortProperty,
        String sortDirection,
        Map<String, Set<String>> filters,
        Boolean favourite,
        String continent,
        String roastProfile
) {}
