package coffeebase.api.utils;

import coffeebase.api.domain.brew.model.*;

import java.util.ArrayList;
import java.util.List;

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
                BrewStatus.STARTED,
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
                BrewStatus.IN_PROGRESS,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static BrewDTO createBrewDTOWithPourOvers(Long id) {
        List<PourOverDTO> pourOvers = List.of(
                createPourOver30Sec100MlWithComment("comment1"),
                createPourOver30Sec100MlWithComment("comment2"),
                createPourOver30Sec100MlWithComment("comment3")
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
                BrewStatus.COMPLETED,
                pourOvers,
                new ArrayList<>()
        );
    }

    private static PourOverDTO createPourOver30Sec100MlWithComment(String comment) {
        return new PourOverDTO(30L, 100, comment, null);
    }
}
