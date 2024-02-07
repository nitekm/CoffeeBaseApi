package coffeebase.api.domain.coffee.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CoffeeRequestFilterProcessor {

    public static boolean shouldApplyFilters(Map<String, List<String>> filters) {
        return filters != null && filters.values().stream().anyMatch(list -> !list.isEmpty());
    }
    public static List<Boolean> processFavourite(List<String> favouriteFilters) {
        if (favouriteFilters == null || favouriteFilters.isEmpty()) {
            return Collections.singletonList(false);
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

    public static List<String> processFilters(String key, List<String> filters) {
       if (filters == null || filters.isEmpty()) {
           return switch (key) {
               case "continent" -> Collections.singletonList("Continent");
               case "roastProfile" -> Collections.singletonList("Roast Profile");
               default -> filters;
           };
       }
       return filters;
    }
}
