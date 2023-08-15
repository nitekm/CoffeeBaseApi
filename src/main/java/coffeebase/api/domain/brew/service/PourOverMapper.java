package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.model.PourOver;
import coffeebase.api.domain.brew.model.PourOverDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PourOverMapper {

    @Mapping(target = "brew", ignore = true)
    PourOverDTO toDTO(PourOver pourOver);
    PourOver toEntity(PourOverDTO dto);
}
