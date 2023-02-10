package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeController;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final CoffeeMappingService mappingService;

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    public CoffeeService(final CoffeeRepository coffeeRepository, final CoffeeMappingService mappingService) {
        this.coffeeRepository = coffeeRepository;
        this.mappingService = mappingService;
    }

    public List<CoffeeDTO> getAllCoffees() {
        var user = getUserFromRequest();
        log.debug("Getting all coffees for user" + user.getUserId() + " CALLED!");

        var allUserCoffees = coffeeRepository.findAllByUser(user)
                .stream()
                .map(mappingService::mapForList)
                .collect(Collectors.toList());

        return allUserCoffees;
    }

    public List<CoffeeDTO> search(String content) {
        var user = getUserFromRequest();
        log.debug("Searching by" + content + " CALLED!");
        var searchResult = coffeeRepository.findByFields(content, user)
                .stream()
                .map(mappingService::mapForList)
                .collect(Collectors.toList());

        return searchResult;
    }

    public CoffeeDTO getCoffeeById(int id) {
        log.debug("Getting coffee with id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(mappingService::mapSingleWithImage)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO addCoffee(CoffeeDTO source, MultipartFile image) {
        var user = getUserFromRequest();

        var mappedCoffee = mappingService.mapUserToCoffee(source, user);
        var withImage = mappingService.mapImageToCoffee(mappedCoffee, image);

        var savedCoffee = coffeeRepository.save(withImage);
        log.info("Saving new coffee for user: " + user.getUserId() + " CALLED");

        var coffeeDTO = mappingService.mapSingleWithImage(savedCoffee);

        return coffeeDTO;
    }

    public CoffeeDTO updateCoffee(int id, CoffeeDTO update, MultipartFile image) {
        var updatedCoffee = coffeeRepository.findById(id)
                .map(coffee -> mappingService.mapUpdateCoffeeData(coffee, update, image))
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));

        var savedUpdate = coffeeRepository.save(updatedCoffee);

        log.info("Updating coffee with id: " + id + " CALLED!");
        var coffeeDTO = mappingService.mapSingleWithImage(savedUpdate);

        return coffeeDTO;
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

        var coffeeDTO = mappingService.mapSingleWithImage(updatedCoffee);

        return coffeeDTO;
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

    private User getUserFromRequest() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
