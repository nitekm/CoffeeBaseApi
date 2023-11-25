package coffeebase.api.domain.brew.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BrewMethod {
    @JsonProperty("aeropress")
    AEROPRESS,
    @JsonProperty("pour-over machine")
    POUR_OVER_MACHINE,
    @JsonProperty("chemex")
    CHEMEX,
    @JsonProperty("espresso machine")
    ESPRESSO_MACHINE,
    @JsonProperty("french press")
    FRENCHPRESS,
    @JsonProperty("mokka pot")
    MOKKA_POT,
    @JsonProperty("V60")
    V60
}
