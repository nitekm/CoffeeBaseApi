package coffeebase.api.domain.coffee.model;

import coffeebase.api.domain.tag.model.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface CoffeeMapper {

    @Mapping(target = "userId", source = "coffee.user.userId")
    @Mapping(target = "tags.coffees", ignore = true)
    CoffeeDTO toDTO(Coffee coffee);

    @Mapping(target = "id", ignore = true)
    Coffee toCoffee(CoffeeDTO coffeeDTO);
}
