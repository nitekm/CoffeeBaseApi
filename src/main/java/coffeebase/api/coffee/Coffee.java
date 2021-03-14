package coffeebase.api.coffee;

import coffeebase.api.audit.Audit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "coffees")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String origin;
    private String roaster;
    private String rating;
    private String imageUrl;
    private boolean favourite;
    @Embedded
    Audit audit = new Audit();

    public Coffee() {
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getRoaster() {
        return roaster;
    }

    void setRoaster(final String roaster) {
        this.roaster = roaster;
    }

    public String getRating() {
        return rating;
    }

    void setRating(final String rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavourite() {
        return favourite;
    }

    void setFavourite(final boolean favourite) {
        this.favourite = favourite;
    }

    public void updateCoffee(Coffee updatedCoffee) {
        name = updatedCoffee.name;
        origin = updatedCoffee.origin;
        roaster = updatedCoffee.roaster;
        rating = updatedCoffee.rating;
        imageUrl = updatedCoffee.imageUrl;
        favourite = updatedCoffee.favourite;
    }
}
