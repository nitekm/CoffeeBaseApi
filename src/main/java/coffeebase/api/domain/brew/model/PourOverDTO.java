package coffeebase.api.domain.brew.model;

public record PourOverDTO(
        Long timeInSeconds,
        Integer waterAmountInMl,
        String comment,
        BrewDTO brew
)
{}
