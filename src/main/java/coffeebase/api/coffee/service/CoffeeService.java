package coffeebase.api.coffee.service;

import coffeebase.api.coffee.controller.CoffeeController;
import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.model.CoffeeDTO;
import coffeebase.api.coffee.model.CoffeeMapper;
import coffeebase.api.coffee.repository.CoffeeRepository;
import coffeebase.api.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    public final CoffeeMapper coffeeMapper;
    private final CoffeeRepository coffeeRepository;

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    CoffeeService(final CoffeeMapper coffeeMapper, final CoffeeRepository coffeeRepository) {
        this.coffeeMapper = coffeeMapper;
        this.coffeeRepository = coffeeRepository;
    }

    public List<Coffee> getAllCoffees() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return coffeeRepository.findAll().stream()
                .filter(coffee -> coffee.getUserId() != null)
                .filter(coffee -> coffee.getUserId().equalsIgnoreCase(user.getUserId()))
                .collect(Collectors.toList());
    }

    public List<Coffee> getAllCoffeesSortByNameAsc() {
        return coffeeRepository.findAllByOrderByNameAsc();
    }

    public List<Coffee> getAllCoffeesSortByNameDesc() {
        return coffeeRepository.findAllByOrderByNameDesc();
    }

    public List<Coffee> getAllCoffeesSortByRatingAsc() {
        return coffeeRepository.findAllByOrderByRatingAsc();
    }

    public List<Coffee> getAllCoffeesSortByRatingDesc() {
        return coffeeRepository.findAllByOrderByRatingDesc();
    }

    public Coffee getCoffeeById(int id) {
        return coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO addCoffee(CoffeeDTO source) {
        var result = coffeeRepository.save(coffeeMapper.toCoffee(source));
        return coffeeMapper.toDTO(result);
    }

    public void switchFavourite(int id) {
        var coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        coffee.setFavourite(!coffee.isFavourite());
        coffeeRepository.save(coffee);
    }

    public void deleteCoffee(int id) {
//        coffeeRepository.findById(id).ifPresent(coffee -> {
//            coffee.getCoffeeGroups().forEach(coffeeGroup -> getAllCoffees().remove(coffee));
//            coffeeRepository.deleteById(id);
//        });
        coffeeRepository.findById(id)
                .ifPresent(coffee -> coffeeRepository.deleteById(id));
    }

    //TODO: maybe wrong
    public void updateCoffee(int id, CoffeeDTO toUpdate) {
        coffeeRepository.findById(id)
                .ifPresent(coffee -> {
                    coffeeRepository.save(coffee);
                });
    }
}
