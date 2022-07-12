package coffeebase.api.coffee.model;

import coffeebase.api.audit.Audit;
import coffeebase.api.security.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "COFFEES")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String origin;
    private String roaster;
    private int rating;
    private String imageUrl;
    private boolean favourite;
    @Column(name = "plain_user_id")
    private String userId;
    @ManyToOne
    private User User;
    @Embedded
    private Audit audit = new Audit();

    public Coffee() {
    }

    public Coffee(final String name, final String origin, final String roaster, final int rating, final String imageUrl, final String userId) {
        this.name = name;
        this.origin = origin;
        this.roaster = roaster;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public void setFavourite(final boolean favourite) {
        this.favourite = favourite;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public coffeebase.api.security.model.User getUser() {
        return User;
    }

    public void setUser(final coffeebase.api.security.model.User user) {
        User = user;
    }

}