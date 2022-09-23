package coffeebase.api.domain.coffee;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.coffee.model.CoffeeMapper;
import coffeebase.api.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    public final CoffeeMapper coffeeMapper;
    private final CoffeeRepository coffeeRepository;

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    public CoffeeService(final CoffeeMapper coffeeMapper, final CoffeeRepository coffeeRepository) {
        this.coffeeMapper = coffeeMapper;
        this.coffeeRepository = coffeeRepository;
    }

    public List<CoffeeDTO> getAllCoffees() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Getting all coffees for user" + user.getUserId() + " CALLED!");
        return coffeeRepository.findAll()
                .stream()
                .filter(coffee -> coffee.getUser() != null)
                .filter(coffee -> coffee.getUser().getUserId().equalsIgnoreCase(user.getUserId()))
                .map(coffeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CoffeeDTO> search(String content) {
        return coffeeRepository.findByFields(content).stream()
                .map(coffeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CoffeeDTO getCoffeeById(int id) {
        log.info("Getting coffee with id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(coffeeMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO addCoffee(CoffeeDTO source) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        source.setUser(user);
        source.setUserId(user.getUserId());
        var mappedCoffee = coffeeMapper.toCoffee(source);
        var savedCoffee = coffeeRepository.save(mappedCoffee);
        log.info("Saving new coffee for user: " + user.getUserId() + " CALLED");
        return coffeeMapper.toDTO(savedCoffee);
    }

    public CoffeeDTO switchFavourite(int id) {
        var coffee = coffeeRepository.findById(id)
                .map(dbCoffee -> {
                    dbCoffee.setFavourite(!dbCoffee.isFavourite());
                    return dbCoffee;
                })
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        var updatedCoffee = coffeeRepository.save(coffee);
        log.info("Switching favourites for coffee id: " + id + " CALLED!");
        return coffeeMapper.toDTO(updatedCoffee);
    }

    public void deleteCoffee(int id) {
        coffeeRepository.findById(id)
                .map(coffee -> {
                    coffeeRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        log.info("Delete coffee with id: " + id + "CALLED!");
    }

    public CoffeeDTO updateCoffee(int id, CoffeeDTO toUpdate) {
        var updatedCoffee = coffeeRepository.findById(id)
                .map(coffee -> updateCoffeeData(coffee, toUpdate))
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));

        log.info("Updating coffee with id: " + id + " CALLED!");
        return coffeeMapper.toDTO(updatedCoffee);
    }

    private Coffee updateCoffeeData(Coffee coffee, CoffeeDTO update) {
        Optional.ofNullable(update.getName()).ifPresent(coffee::setName);
        Optional.ofNullable(update.getOrigin()).ifPresent(coffee::setOrigin);
        Optional.ofNullable(update.getRoaster()).ifPresent(coffee::setRoaster);
        Optional.of(update.getRating()).ifPresent(coffee::setRating);
        Optional.ofNullable(update.getImageUrl()).ifPresent(coffee::setImageUrl);

        return coffeeRepository.save(coffee);
    }
}
