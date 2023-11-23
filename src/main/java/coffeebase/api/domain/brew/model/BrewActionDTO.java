package coffeebase.api.domain.brew.model;

public record BrewActionDTO(
        BrewActionType brewActionType,
        Long coffeeId,
        Long brewId)
{ }
