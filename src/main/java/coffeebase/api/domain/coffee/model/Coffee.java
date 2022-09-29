package coffeebase.api.domain.coffee.model;

import coffeebase.api.audit.Audit;
import coffeebase.api.domain.enums.Continent;
import coffeebase.api.domain.enums.RoastProfile;
import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.security.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "COFFEES")
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private Double rating;
    private Boolean favourite;
    private String imageUrl;
    private String origin;
    private String roaster;
    private String processing;
    private RoastProfile roastProfile;
    private String region;
    private Continent continent;
    private String farm;
    private Integer cropHeight;
    private Integer scaRating;

    @OneToMany(mappedBy = "coffee", cascade = CascadeType.PERSIST)
    private List<Tag> tags;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user.id")
    private User user;

    @Embedded
    private Audit audit = new Audit();

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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(final boolean favourite) {
        this.favourite = favourite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
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

    public RoastProfile getRoastProfile() {
        return roastProfile;
    }

    public void setRoastProfile(final RoastProfile roastProfile) {
        this.roastProfile = roastProfile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(final Continent continent) {
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

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(final Boolean favourite) {
        this.favourite = favourite;
    }

    public Integer getScaRating() {
        return scaRating;
    }

    public void setScaRating(final Integer scaRating) {
        this.scaRating = scaRating;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(final Audit audit) {
        this.audit = audit;
    }
}
