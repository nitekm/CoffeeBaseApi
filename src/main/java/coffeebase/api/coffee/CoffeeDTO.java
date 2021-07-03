package coffeebase.api.coffee;

import javax.validation.constraints.NotBlank;

public class CoffeeDTO {

    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String origin;
    private String roaster;
    private int rating;
    private String imageUrl;
    private boolean favourite;

    public CoffeeDTO() {}

    public CoffeeDTO(Coffee source) {
        favourite = source.isFavourite();
        id = source.getId();
        name = source.getName();
        origin = source.getOrigin();
        roaster = source.getRoaster();
        rating = source.getRating();
        imageUrl = source.getImageUrl();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getRoaster() {
        return roaster;
    }

    public void setRoaster(final String roaster) {
        this.roaster = roaster;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavourite() {
        return favourite;
    }
    
    public Coffee toCoffee() {
        return new Coffee(name, origin, roaster, rating, imageUrl);
    }
}
