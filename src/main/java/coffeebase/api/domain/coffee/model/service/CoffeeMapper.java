package coffeebase.api.domain.coffee.model.service;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.tag.model.TagMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface CoffeeMapper {

    @Mapping(target = "userId", source = "coffee.user.userId")
    @Mapping(target = "tags.coffees", ignore = true)
    CoffeeDTO coffeeToDTO(Coffee coffee);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", ignore = false)
    @Mapping(target = "favourite", ignore = false)
    CoffeeDTO dtoForCoffeeList(Coffee coffee);

    @Mapping(target = "id", ignore = true)
    Coffee dtoToCoffee(CoffeeDTO coffeeDTO);
}
