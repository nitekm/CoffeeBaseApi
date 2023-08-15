package coffeebase.api.utils;

import coffeebase.api.domain.brew.model.BrewDTO;

public class TestBrewUtils {

    public static BrewDTO createEmptyBrewDTO() {
        return new BrewDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
