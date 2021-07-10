package coffeebase.api.coffee;

import javax.validation.constraints.NotBlank;

public class CoffeeDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String origin;
    private String roaster;
    private int rating;
    private String imageUrl;

    public CoffeeDTO() {}

    public CoffeeDTO(Coffee source) {
        name = source.getName();
        origin = source.getOrigin();
        roaster = source.getRoaster();
        rating = source.getRating();
        imageUrl = source.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getRoaster() {
        return roaster;
    }

    public int getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    Coffee toCoffee() {
        return new Coffee(name, origin, roaster, rating, imageUrl);
    }
}
