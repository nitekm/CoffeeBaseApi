package coffeebase.api.coffee.model;

public class CoffeeDTO {

    private String name;
    private String origin;
    private String roaster;
    private int rating;
    private String imageUrl;
    private String userId;

    public CoffeeDTO() {}

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

    public String getUserId() {
        return userId;
    }
}
