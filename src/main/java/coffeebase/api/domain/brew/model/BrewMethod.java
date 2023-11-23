package coffeebase.api.domain.brew.model;

public enum BrewMethod {
    V60("V60"),
    AEROPRESS("aeropress"),
    COFFEE_MACHINE("espresso machine"),
    FRENCHPRESS("french press"),
    CAFETIERE("mokka pot"),
    TURKISH_CAFE("tuklf");

    BrewMethod(String value) {
        this.value = value;
    }

    private String value;

    private String getValue() {
        return value;
    }
}
