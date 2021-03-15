package coffeebase.api.coffee;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private CoffeeRepository repository;
    private CoffeeService service;

    CoffeeController(final CoffeeRepository repository, final CoffeeService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<Coffee>> getAllCoffees() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Coffee> getCoffee(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Coffee> addCoffee(@RequestBody @Valid Coffee coffee) {
        var result = repository.save(coffee);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCoffee(@PathVariable int id, @RequestBody @Valid Coffee toUpdate) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(coffee -> {
            coffee.updateCoffee(toUpdate);
            repository.save(coffee);
        });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(coffee -> repository.deleteById(id));
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    ResponseEntity<?> changeFavourite(@PathVariable int id) {
        service.switchFavourite(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}/{groupName}")
    ResponseEntity<?> addToGroup(@PathVariable int id, @PathVariable String groupName) {
        service.addToGroup(id, groupName);
        return ResponseEntity.noContent().build();
    }
}
