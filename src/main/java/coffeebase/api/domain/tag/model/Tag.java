package coffeebase.api.domain.tag.model;

import coffeebase.api.domain.base.model.BaseEntity;
import coffeebase.api.domain.coffee.model.Coffee;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Tag extends BaseEntity<Long> {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @ManyToMany(mappedBy = "tags")
    private List<Coffee> coffees;
}
