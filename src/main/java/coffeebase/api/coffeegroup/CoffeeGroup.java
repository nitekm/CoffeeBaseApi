package coffeebase.api.coffeegroup;

import coffeebase.api.coffee.Coffee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coffee_groups")
public class CoffeeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private GroupType groupType;
    @ManyToMany(mappedBy = "coffeeGroups")
    private Set<Coffee> coffees;

    public enum GroupType {
        METHOD, ORIGIN, ROASTER
    }

    public CoffeeGroup() {
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    void setGroupType(final GroupType type) {
        this.groupType = type;
    }

    public Set<Coffee> getCoffees() {
        return coffees;
    }

    void setCoffees(final Set<Coffee> coffees) {
        this.coffees = coffees;
    }
}
