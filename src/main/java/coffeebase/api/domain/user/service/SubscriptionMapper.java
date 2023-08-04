package coffeebase.api.domain.user.service;

import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.domain.user.model.Subscription;
import coffeebase.api.domain.user.model.SubscriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDTO toDTO(Subscription subscription);
    Subscription toEntity(SubscriptionDTO dto);
}