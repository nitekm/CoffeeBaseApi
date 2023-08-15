package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = PourOverMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrewMapper {

    BrewDTO toDTO(Brew brew);
    Brew toEntity(BrewDTO brewDTO);

    @Mapping(target = "id", ignore = true)
    Brew toEntityOmitId(BrewDTO brewDTO);
}
