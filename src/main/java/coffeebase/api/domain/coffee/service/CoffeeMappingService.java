package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.model.Tag;
import coffeebase.api.domain.tag.model.TagMapper;
import coffeebase.api.security.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CoffeeMappingService {

    private final CoffeeMapper coffeeMapper;
    private final CoffeeBaseFileService fileService;
    private final TagMapper tagMapper;

    public CoffeeMappingService(final CoffeeMapper coffeeMapper, final CoffeeBaseFileService fileService, final TagMapper tagMapper) {
        this.coffeeMapper = coffeeMapper;
        this.fileService = fileService;
        this.tagMapper = tagMapper;
    }

    public CoffeeDTO mapCoffee(Coffee coffee) {
        var coffeeDTO = coffeeMapper.coffeeToDTO(coffee);
        return coffeeDTO;
    }

    public CoffeeDTO mapForList(Coffee coffee) {
        var coffeeDTO = coffeeMapper.dtoForCoffeeList(coffee);
        return coffeeDTO;
    }

    public Coffee mapCoffeeToSave(CoffeeDTO coffeeDTO, User user, MultipartFile image) {
        var coffee = new Coffee();
        coffee = mapUserToCoffee(coffeeDTO, user);
        mapImageToCoffee(coffee, image);
        return coffee;
    }


    private Coffee mapUserToCoffee(CoffeeDTO coffeeDTO, User user) {
//        coffeeDTO.user(user);
//        coffeeDTO.setUserId(user.getUserId());
//        coffeeDTO.getTags().forEach(tag -> {
//            tag.setUser(user);
//            tag.setUserId(user.getUserId());
//        });

        final var coffee = coffeeMapper.dtoToCoffee(coffeeDTO);
        coffee.setUser(user);
        coffee.getTags().forEach(tag -> tag.setUser(user));

        return coffee;
    }

    private Coffee mapImageToCoffee(Coffee coffee, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return coffee;
        }
        final var savedImage = fileService.save(image);
        coffee.setCoffeeBaseFile(savedImage);

        return coffee;
    }

    public Coffee mapUpdateCoffeeData(Coffee coffee, CoffeeDTO update, MultipartFile image) {
        Optional.ofNullable(update.name()).ifPresent(coffee::setName);
        Optional.ofNullable(update.origin()).ifPresent(coffee::setOrigin);
        Optional.ofNullable(update.roaster()).ifPresent(coffee::setRoaster);
        Optional.of(update.rating()).ifPresent(coffee::setRating);
        Optional.ofNullable(update.processing()).ifPresent(coffee::setProcessing);
        Optional.ofNullable(update.roastProfile()).ifPresent(coffee::setRoastProfile);
        Optional.ofNullable(update.region()).ifPresent(coffee::setRegion);
        Optional.ofNullable(update.continent()).ifPresent(coffee::setContinent);
        Optional.ofNullable(update.farm()).ifPresent(coffee::setFarm);
        Optional.ofNullable(update.cropHeight()).ifPresent(coffee::setCropHeight);
        Optional.ofNullable(update.scaRating()).ifPresent(coffee::setScaRating);
        final List<Tag> tags = update.tags().stream()
//                .peek(tagDTO -> tagDTO.setUser(coffee.getUser()))
                .map(tagMapper::toTag)
                .peek(tag -> tag.setUser(coffee.getUser()))
                .collect(Collectors.toList());
        coffee.setTags(tags);

        mapImageToCoffee(coffee, image);

        return coffee;
    }
}

