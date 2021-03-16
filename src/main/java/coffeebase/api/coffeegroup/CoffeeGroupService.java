package coffeebase.api.coffeegroup;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeGroupService {
    private CoffeeGroupRepository groupRepository;

    CoffeeGroupService(final CoffeeGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    List<CoffeeGroupDTO> getAllCoffeeGroups() {
        return groupRepository.findAll()
                .stream()
                .map(CoffeeGroupDTO::new)
                .collect(Collectors.toList());
    }

    CoffeeGroupDTO getCoffeeGroupById(int id) {
        return groupRepository.findById(id)
                .map(CoffeeGroupDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Coffee Group with given id not found"));
    }

    CoffeeGroupDTO addCoffeeGroup(CoffeeGroupDTO source) {
        var result = groupRepository.save(source.toGroup());
        return new CoffeeGroupDTO(result);
    }
}
