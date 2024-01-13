package coffeebase.api.domain.brew.model;

import coffeebase.api.domain.base.model.BaseEntity;
import coffeebase.api.domain.coffee.model.Coffee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Brew extends BaseEntity<Long> {

    private String name;

    @Enumerated(EnumType.STRING)
    private BrewMethod method;

    @Min(1)
    @Max(5000)
    private Integer waterAmountInMl;

    @Min(1)
    @Max(100)
    private Integer waterTemp;

    @Min(1)
    @Max(250)
    private Integer coffeeWeightInGrams;

    @Min(1)
    @Max(100)
    private Integer grinderSetting;

    private String filter;

    private String totalTime;

    @Enumerated(EnumType.STRING)
    private BrewStatus status;

    @OneToMany(mappedBy = "brew", cascade = CascadeType.ALL)
    private List<PourOver> pourOvers = new ArrayList<>();

    @ManyToMany(mappedBy = "brews")
    private List<Coffee> coffees;
}



