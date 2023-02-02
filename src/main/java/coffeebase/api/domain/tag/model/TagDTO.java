package coffeebase.api.domain.tag.model;

import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.security.model.User;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class TagDTO {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    private List<CoffeeDTO> coffees;

    private String userId;

    private User user;

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

    public List<CoffeeDTO> getCoffees() {
        return coffees;
    }

    public void setCoffees(final List<CoffeeDTO> coffees) {
        this.coffees = coffees;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
