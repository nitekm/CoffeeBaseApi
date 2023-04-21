package coffeebase.api.domain.file;

import coffeebase.api.domain.base.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeBaseFile extends BaseEntity<Long> {

    private String name;
    private String path;
}
