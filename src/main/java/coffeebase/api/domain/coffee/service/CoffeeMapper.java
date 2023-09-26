package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.brew.service.BrewMapper;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.tag.service.TagMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class, BrewMapper.class})
public interface CoffeeMapper {

    @Mapping(target = "tags.coffees", ignore = true)
    @Mapping(target = "brews.coffees", ignore = true)
    @Mapping(target = "coffeeImageName", source = "coffee.coffeeBaseFile.name")
    CoffeeDTO coffeeToDTO(Coffee coffee);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", ignore = false)
    @Mapping(target = "favourite", ignore = false)
    @Mapping(target = "id", ignore = false)
    @Mapping(target = "coffeeImageName", ignore = false, source = "coffee.coffeeBaseFile.name")
    CoffeeDTO dtoForCoffeeList(Coffee coffee);

    Coffee dtoToCoffee(CoffeeDTO coffeeDTO);
}
