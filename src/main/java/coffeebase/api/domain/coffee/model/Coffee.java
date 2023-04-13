package coffeebase.api.domain.coffee.model;

import coffeebase.api.audit.Audit;
import coffeebase.api.domain.file.CoffeeBaseFile;
import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.security.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "coffees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 0)
    @Max(value = 6)
    private Double rating;

    private Boolean favourite;

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

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "coffee_tag",
            joinColumns = @JoinColumn(name = "coffee_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "file_id")
    private CoffeeBaseFile coffeeBaseFile;

    @Embedded
    private Audit audit = new Audit();
}
