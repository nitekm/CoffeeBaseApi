package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.coffee.service.CoffeeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {PourOverMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrewMapper {

    @Mapping(target = "coffees", ignore = true)
    BrewDTO toDTO(Brew brew);
    Brew toEntity(BrewDTO brewDTO);

    @Named("OmitId")
    @Mapping(target = "id", ignore = true)
    Brew toEntityOmitId(BrewDTO brewDTO);
}
