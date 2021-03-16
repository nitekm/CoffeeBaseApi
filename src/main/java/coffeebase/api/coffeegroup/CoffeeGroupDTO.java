package coffeebase.api.coffeegroup;

import coffeebase.api.coffee.Coffee;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CoffeeGroupDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private CoffeeGroup.GroupType groupType;
    private Set<Coffee> coffees;

    public CoffeeGroupDTO() {}

    public CoffeeGroupDTO(CoffeeGroup source) {
        name = source.getName();
        groupType = source.getGroupType();
        coffees = source.getCoffees();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public CoffeeGroup.GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(final CoffeeGroup.GroupType groupType) {
        this.groupType = groupType;
    }

    public Set<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(final Set<Coffee> coffees) {
        this.coffees = coffees;
    }

    public CoffeeGroup toGroup() {
        return new CoffeeGroup(name, groupType);
    }
}
