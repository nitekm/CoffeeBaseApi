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

    CoffeeGroupController(final CoffeeGroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<List<CoffeeGroup>> findAllGroups() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<CoffeeGroup> findById(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<CoffeeGroup> createCoffeeGroup(@RequestBody @Valid CoffeeGroup coffeeGroup) {
        var result = repository.save(coffeeGroup);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
