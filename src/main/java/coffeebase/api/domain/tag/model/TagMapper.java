package coffeebase.api.domain.tag.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "coffees", ignore = true)
    TagDTO toDTO(Tag tag);

    Tag toTag(TagDTO tagDTO);
}
