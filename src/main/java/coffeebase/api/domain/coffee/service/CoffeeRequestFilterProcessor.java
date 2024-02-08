package coffeebase.api.domain.coffee.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoffeeRequestFilterProcessor {

    public static boolean shouldApplyFilters(Map<String, Set<String>> filters) {
        return filters != null && filters.values().stream().anyMatch(list -> !list.isEmpty());
    }
    public static List<Boolean> processFavourite(Set<String> favouriteFilters) {
        if (favouriteFilters == null || favouriteFilters.isEmpty()) {
            return List.of(true, false);
        }
        List<Boolean> processedFilters = new ArrayList<>();
        favouriteFilters.forEach(filter -> {
            if ("yes".equalsIgnoreCase(filter)) {
                processedFilters.add(true);
            } else if ("no".equalsIgnoreCase(filter)) {
                processedFilters.add(false);
            }
        });
        return processedFilters;
    }

    public static Set<String> processFilters(String key, Set<String> filters) {
       if (filters == null || filters.isEmpty()) {
           return switch (key) {
               case "continent" -> Stream.of("Continent", "Asia", "Africa", "South America")
                       .collect(Collectors.toSet());
               case "roastProfile" -> Stream.of("Roast Profile", "Light", "Dark", "Omniroast")
                       .collect(Collectors.toSet());
               default -> filters;
           };
       }
       return filters;
    }
}
