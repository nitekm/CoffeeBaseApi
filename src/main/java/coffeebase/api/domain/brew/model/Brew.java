package coffeebase.api.domain.brew.model;

import coffeebase.api.domain.base.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Brew extends BaseEntity<Long> {

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private BrewMethod method;

    @Min(0)
    @Max(1500)
    private Integer waterAmountInMl;

    @Min(0)
    @Max(100)
    private Integer waterTemp;

    @Min(0)
    @Max(250)
    private Integer coffeeWeightInGrams;

    @Min(0)
    @Max(100)
    private Integer grinderSetting;

    private String filter;

    @Enumerated(EnumType.ORDINAL)
    private BrewStatus status;

    @OneToMany(mappedBy = "brew", cascade = CascadeType.ALL)
    private Set<PourOver> pourOvers = new HashSet<>();
}



