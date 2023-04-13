package coffeebase.api.domain.file;

import coffeebase.api.audit.Audit;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeBaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Audit audit;

    private String name;
    private String path;
}
