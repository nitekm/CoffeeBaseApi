package coffeebase.api.domain.tag.model;

import coffeebase.api.domain.coffee.model.Coffee;

public class TagDTO {

    private Integer id;
    private String name;
    private String color;
    private Coffee coffee;

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
