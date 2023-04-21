package coffeebase.api.domain.tag.service;

import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.domain.tag.model.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "coffees", ignore = true)
    TagDTO toDTO(Tag tag);

    Tag toTag(TagDTO tagDTO);
}
