package coffeebase.api.coffee;

import coffeebase.api.coffeegroup.CoffeeGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private CoffeeRepository coffeeRepository;
    private CoffeeGroupRepository groupRepository;

    CoffeeService(final CoffeeRepository coffeeRepository, final CoffeeGroupRepository groupRepository) {
        this.coffeeRepository = coffeeRepository;
        this.groupRepository = groupRepository;
    }

    //TODO: add allowmultiplecoffestogroupofsametype property

    List<CoffeeDTO> getAllCoffees() {
        return coffeeRepository.findAll()
                .stream()
                .map(CoffeeDTO::new)
                .collect(Collectors.toList());
    }

    CoffeeDTO getCoffeeById(int id) {
        return coffeeRepository.findById(id)
                .map(CoffeeDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    CoffeeDTO addCoffee(CoffeeDTO source) {
        var result = coffeeRepository.save(source.toCoffee());
        return new CoffeeDTO(result);
    }

    void switchFavourite(int id) {
        var coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        coffee.setFavourite(!coffee.isFavourite());
        coffeeRepository.save(coffee);
        }

    void addToGroup(int id, String groupName) {
            var coffee = coffeeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
            var coffeeGroup = groupRepository.findByName(groupName)
                    .orElseThrow(() -> new IllegalArgumentException("Given group does not exists"));
            coffee.getCoffeeGroups().add(coffeeGroup);
            coffeeRepository.save(coffee);
        }

    void updateCoffee(int id, CoffeeDTO toUpdate) {
            coffeeRepository.findById(id)
                .ifPresent(coffee -> {
                coffee.updateCoffee(toUpdate);
                coffeeRepository.save(coffee);
        });
    }
}
