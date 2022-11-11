package coffeebase.api.domain.tag.model;

import coffeebase.api.audit.Audit;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.security.model.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user.id")
    private User user;

    @Embedded
    private Audit audit = new Audit();

    @ManyToMany(mappedBy = "tags")
    private List<Coffee> coffees;

    public Tag() {
    }

    public Tag(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(final List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(final Audit audit) {
        this.audit = audit;
    }
}
