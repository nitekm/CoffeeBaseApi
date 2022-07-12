package coffeebase.api.coffee.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    CoffeeDTO toDTO(Coffee coffee);

    @Mapping(target = "id", ignore = true)
    Coffee toCoffee(CoffeeDTO coffeeDTO);
}
