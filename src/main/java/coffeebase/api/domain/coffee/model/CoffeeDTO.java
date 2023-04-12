package coffeebase.api.domain.coffee.model;

import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.security.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CoffeeDTO {
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 0)
    @Max(value = 6)
    private Double rating;

    private Boolean favourite;

    private String coffeeImageName;

    private String origin;

    private String roaster;

    private String processing;

    private String roastProfile;

    private String region;

    private String continent;

    private String farm;

    @Min(value = 0)
    @Max(value = 8849)
    private Integer cropHeight;

    @Min(value = 0)
    @Max(value = 100)
    private Integer scaRating;

    private String userId;

    private User user;

    private List<TagDTO> tags;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(final Boolean favourite) {
        this.favourite = favourite;
    }

    public String getCoffeeImageName() {
        return coffeeImageName;
    }

    public void setCoffeeImageName(final String coffeeImageName) {
        this.coffeeImageName = coffeeImageName;
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

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(final String processing) {
        this.processing = processing;
    }

    public String getRoastProfile() {
        return roastProfile;
    }

    public void setRoastProfile(final String roastProfile) {
        this.roastProfile = roastProfile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String  getContinent() {
        return continent;
    }

    public void setContinent(final String continent) {
        this.continent = continent;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(final String farm) {
        this.farm = farm;
    }

    public Integer getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(final Integer cropHeight) {
        this.cropHeight = cropHeight;
    }

    public Integer getScaRating() {
        return scaRating;
    }

    public void setScaRating(final Integer scaRating) {
        this.scaRating = scaRating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(final List<TagDTO> tags) {
        this.tags = tags;
    }
}
