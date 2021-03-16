package coffeebase.api.coffeegroup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class CoffeeGroupController {
    private CoffeeGroupRepository repository;
    private CoffeeGroupService service;

    CoffeeGroupController(final CoffeeGroupRepository repository, final CoffeeGroupService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<CoffeeGroupDTO>> findAllGroups() {
        return ResponseEntity.ok(service.getAllCoffeeGroups());
    }

    @GetMapping("/{id}")
    ResponseEntity<CoffeeGroupDTO> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCoffeeGroupById(id));
    }

    @PostMapping
    ResponseEntity<CoffeeGroupDTO> createCoffeeGroup(@RequestBody @Valid CoffeeGroupDTO coffeeGroup) {
        var result = service.addCoffeeGroup(coffeeGroup);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCoffeeGroup(@PathVariable int id) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(group -> repository.deleteById(id));
        return ResponseEntity.noContent().build();
    }
}
