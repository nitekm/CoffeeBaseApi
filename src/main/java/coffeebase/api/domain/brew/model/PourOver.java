package coffeebase.api.domain.brew.model;

import coffeebase.api.domain.base.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PourOver extends BaseEntity<Long> {

    @Min(1)
    @Max(600)
    private Duration timeInSeconds;

    @Min(1)
    @Max(1500)
    private Integer waterAmountInMl;

    private String comment;

    @ManyToOne
    private Brew brew;
}
