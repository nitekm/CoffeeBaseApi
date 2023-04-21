package coffeebase.api.domain.file;

import coffeebase.api.domain.base.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CoffeeBaseFile extends BaseEntity<Long> {

    private String name;
    private String path;
}
