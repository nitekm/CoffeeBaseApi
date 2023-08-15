package coffeebase.api.domain.coffee.model;

import coffeebase.api.domain.base.model.BaseEntity;
import coffeebase.api.domain.file.CoffeeBaseFile;
import coffeebase.api.domain.tag.model.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "coffees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Coffee extends BaseEntity<Long> {

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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id")
    private CoffeeBaseFile coffeeBaseFile;
}
