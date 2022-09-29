package coffeebase.api.domain.tag.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag toTag(TagDTO tagDTO);
}
