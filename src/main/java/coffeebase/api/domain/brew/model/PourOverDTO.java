package coffeebase.api.domain.brew.model;

public record PourOverDTO(
         Integer time,
         Integer waterAmountInMl,
         String comment,
         BrewDTO brew
)
{}
