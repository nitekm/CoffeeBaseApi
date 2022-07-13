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

    public CoffeeService(final CoffeeMapper coffeeMapper, final CoffeeRepository coffeeRepository) {
        this.coffeeMapper = coffeeMapper;
        this.coffeeRepository = coffeeRepository;
    }

    public List<CoffeeDTO> getAllCoffees() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return coffeeRepository.findAll()
                .stream()
                .filter(coffee -> coffee.getUserId() != null)
                .filter(coffee -> coffee.getUserId().equalsIgnoreCase(user.getUserId()))
                .map(coffeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CoffeeDTO getCoffeeById(int id) {
        return coffeeRepository.findById(id)
                .map(coffeeMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO addCoffee(CoffeeDTO source) {
        var savedCoffee = coffeeRepository.save(coffeeMapper.toCoffee(source));
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
        return coffeeMapper.toDTO(updatedCoffee);
    }

    public void deleteCoffee(int id) {
        coffeeRepository.findById(id)
                .map(coffee -> {
                    coffeeRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO updateCoffee(int id, CoffeeDTO toUpdate) {
        var updatedCoffee = coffeeRepository.findById(id)
                .map(coffee -> updateCoffeeData(coffee, toUpdate))
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));

        return coffeeMapper.toDTO(updatedCoffee);
    }

    private Coffee updateCoffeeData(Coffee coffee, CoffeeDTO coffeeDTO) {
        var updatedCoffee = coffeeMapper.toCoffee(coffeeDTO);
        updatedCoffee.setId(coffee.getId());

        return coffeeRepository.save(updatedCoffee);
    }
}
