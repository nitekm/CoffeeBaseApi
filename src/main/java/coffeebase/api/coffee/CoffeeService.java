package coffeebase.api.coffee;

import coffeebase.api.coffeegroup.CoffeeGroupRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CoffeeService {
    private CoffeeRepository coffeeRepository;
    private CoffeeGroupRepository groupRepository;

    CoffeeService(final CoffeeRepository coffeeRepository, final CoffeeGroupRepository groupRepository) {
        this.coffeeRepository = coffeeRepository;
        this.groupRepository = groupRepository;
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
}
