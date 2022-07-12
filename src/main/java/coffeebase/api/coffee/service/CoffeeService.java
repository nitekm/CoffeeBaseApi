package coffeebase.api.coffee.service;

import coffeebase.api.coffee.controller.CoffeeController;
import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.model.CoffeeDTO;
import coffeebase.api.coffee.model.CoffeeMapper;
import coffeebase.api.coffee.repository.CoffeeRepository;
import coffeebase.api.coffeegroup.CoffeeGroup;
import coffeebase.api.coffeegroup.CoffeeGroupRepository;
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
    private final CoffeeGroupRepository groupRepository;

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    CoffeeService(final CoffeeMapper coffeeMapper, final CoffeeRepository coffeeRepository, final CoffeeGroupRepository groupRepository) {
        this.coffeeMapper = coffeeMapper;
        this.coffeeRepository = coffeeRepository;
        this.groupRepository = groupRepository;
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

    public void addToGroup(int id, String groupName) {
        var coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        var coffeeGroup = groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalArgumentException("Given group does not exists"));

        //Get all coffee groups types of given coffee
        if (!coffee.getCoffeeGroups().isEmpty()) {
            List<CoffeeGroup.GroupType> coffeeGroupTypes = coffee.getCoffeeGroups()
                    .stream()
                    .map(CoffeeGroup::getGroupType)
                    .collect(Collectors.toList());

            //Make sure coffee is not in given group type already
            if (coffeeGroupTypes.contains(coffeeGroup.getGroupType())) {
                throw new IllegalStateException("Coffee cannot be in 2 groups of the same type");
            }
        }
        coffee.getCoffeeGroups().add(coffeeGroup);
        coffeeRepository.save(coffee);
    }

    public void deleteCoffeeFromGroup(int id, String groupName) {
        var coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        var coffeeGroup = groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalArgumentException("Given group does not exists"));

        if (!coffee.getCoffeeGroups().contains(coffeeGroup) && !coffeeGroup.getCoffees().contains(coffee)) {
            throw new IllegalArgumentException("Coffee is not member of given group");
        }
        else {
            coffee.getCoffeeGroups().remove(coffeeGroup);
            coffeeRepository.save(coffee);
        }
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
