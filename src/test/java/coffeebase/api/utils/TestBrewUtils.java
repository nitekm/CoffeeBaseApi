package coffeebase.api.utils;

import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.model.BrewMethod;
import coffeebase.api.domain.brew.model.PourOver;
import coffeebase.api.domain.brew.model.PourOverDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static BrewDTO createBrewDTOWthId() {
        return new BrewDTO(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static BrewDTO createBrewDTOWithGeneralInfo(Long id) {
        return new BrewDTO(
                id,
                "test name",
                BrewMethod.V60,
                null,
                null,
                null,
                null,
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static BrewDTO createBrewDTOWithIngredients(Long id) {
        return new BrewDTO(
                id,
                null,
                null,
                300,
                82,
                18,
                27,
                "v60 filter",
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static BrewDTO createBrewDTOWithPourOvers(Long id) {
        List<PourOverDTO> pourOvers = List.of(
                createPourOver("comment1"),
                createPourOver("comment2"),
                createPourOver("comment3")
        );
        return new BrewDTO(
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                pourOvers,
                new ArrayList<>()
        );
    }

    private static PourOverDTO createPourOver(String comment) {
        return new PourOverDTO(30, 100, comment, null);
    }
}
