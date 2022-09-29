package coffeebase.api.domain.tag.model;

import coffeebase.api.audit.Audit;
import coffeebase.api.domain.coffee.model.Coffee;

import javax.persistence.*;

@Entity
@Table(name = "TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLOR")
    private String color;

    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

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

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(final Coffee coffee) {
        this.coffee = coffee;
    }
}
