package coffeebase.api.coffeegroup;

import coffeebase.api.exception.IllegalExceptionProcessing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@IllegalExceptionProcessing
@RequestMapping("/groups")
public class CoffeeGroupController {
    private CoffeeGroupRepository repository;
    private CoffeeGroupService service;

    CoffeeGroupController(final CoffeeGroupRepository repository, final CoffeeGroupService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<CoffeeGroupDTO>> getAllCoffeeGroups() {
        return ResponseEntity.ok(service.getAllCoffeeGroups());
    }

    @GetMapping("/{id}")
    ResponseEntity<CoffeeGroupDTO> getCoffeeGroupById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCoffeeGroupById(id));
    }

    @PostMapping
    ResponseEntity<CoffeeGroupDTO> addCoffeeGroup(@RequestBody @Valid CoffeeGroupDTO coffeeGroup) {
        var result = service.addCoffeeGroup(coffeeGroup);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCoffeeGroup(@PathVariable int id) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        service.deleteCoffeeGroup(id);
        return ResponseEntity.noContent().build();
    }
}
