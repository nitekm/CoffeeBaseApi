package coffeebase.api.coffee.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    CoffeeDTO toDTO(Coffee coffee);
    Coffee toCoffee(CoffeeDTO coffeeDTO);
}
