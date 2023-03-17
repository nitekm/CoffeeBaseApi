package coffeebase.api.domain.file;

import coffeebase.api.audit.Audit;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class CoffeeBaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Audit audit;

    private String name;
    private String path;


    public CoffeeBaseFile() {}

    public CoffeeBaseFile(final String name, final String path) {
        this.name = name;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(final Audit audit) {
        this.audit = audit;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }
}
