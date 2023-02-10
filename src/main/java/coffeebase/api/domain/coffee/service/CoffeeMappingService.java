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

    public CoffeeDTO mapSingleWithImage(Coffee coffee) {
        var coffeeDTO = coffeeMapper.coffeeToDTO(coffee);
        return coffeeDTO;
    }

    public CoffeeDTO mapForList(Coffee coffee) {
        var coffeeDTO = coffeeMapper.dtoForCoffeeList(coffee);
        return coffeeDTO;
    }

    public Coffee mapUserToCoffee(CoffeeDTO coffeeDTO, User user) {
        coffeeDTO.setUser(user);
        coffeeDTO.setUserId(user.getUserId());
        coffeeDTO.getTags().forEach(tag -> {
            tag.setUser(user);
            tag.setUserId(user.getUserId());
        });

        final var coffee = coffeeMapper.dtoToCoffee(coffeeDTO);

        return coffee;
    }

    public Coffee mapImageToCoffee(Coffee coffee, MultipartFile image) {
        final var savedImage = fileService.save(image);
        coffee.setCoffeeBaseFile(savedImage);

        return coffee;
    }

    public Coffee mapUpdateCoffeeData(Coffee coffee, CoffeeDTO update, MultipartFile image) {
        Optional.ofNullable(update.getName()).ifPresent(coffee::setName);
        Optional.ofNullable(update.getOrigin()).ifPresent(coffee::setOrigin);
        Optional.ofNullable(update.getRoaster()).ifPresent(coffee::setRoaster);
        Optional.of(update.getRating()).ifPresent(coffee::setRating);
        Optional.ofNullable(update.getProcessing()).ifPresent(coffee::setProcessing);
        Optional.ofNullable(update.getRoastProfile()).ifPresent(coffee::setRoastProfile);
        Optional.ofNullable(update.getRegion()).ifPresent(coffee::setRegion);
        Optional.ofNullable(update.getContinent()).ifPresent(coffee::setContinent);
        Optional.ofNullable(update.getFarm()).ifPresent(coffee::setFarm);
        Optional.ofNullable(update.getCropHeight()).ifPresent(coffee::setCropHeight);
        Optional.ofNullable(update.getScaRating()).ifPresent(coffee::setScaRating);
        final List<Tag> tags = update.getTags().stream()
                .peek(tagDTO -> tagDTO.setUser(coffee.getUser()))
                .map(tagMapper::toTag)
                .collect(Collectors.toList());
        coffee.setTags(tags);

        final var savedImage = fileService.save(image);
        coffee.setCoffeeBaseFile(savedImage);

        return coffee;
    }
}

