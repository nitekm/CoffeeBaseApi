package coffeebase.api.coffee;

import coffeebase.api.exception.IllegalExceptionProcessing;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/coffees")
public class CoffeeController {

    private CoffeeRepository repository;
    private CoffeeService service;

    CoffeeController(final CoffeeRepository repository, final CoffeeService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<CoffeeDTO>> getAllCoffees() {
        return ResponseEntity.ok(service.getAllCoffees());
    }

    @GetMapping("/sort/name_asc")
    ResponseEntity<List<CoffeeDTO>> getAllCoffeesSortByNameAsc() {
        return ResponseEntity.ok(service.getAllCoffeesSortByNameAsc());
    }

    @GetMapping("/sort/name_desc")
    ResponseEntity<List<CoffeeDTO>> getAllCoffeesSortByNameDesc() {
        return ResponseEntity.ok(service.getAllCoffeesSortByNameDesc());
    }

    @GetMapping("/sort/rating_asc")
    ResponseEntity<List<CoffeeDTO>> getAllCoffeesSortByRatingAsc() {
        return ResponseEntity.ok(service.getAllCoffeesSortByRatingAsc());
    }

    @GetMapping("/sort/rating_desc")
    ResponseEntity<List<CoffeeDTO>> getAllCoffeesSortByRatingDesc() {
        return ResponseEntity.ok(service.getAllCoffeesSortByRatingDesc());
    }

    @GetMapping("/{id}")
    ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCoffeeById(id));
    }

    @PostMapping
    ResponseEntity<CoffeeDTO> addCoffee(@RequestBody @Valid CoffeeDTO coffee) {
        var result = service.addCoffee(coffee);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCoffee(@PathVariable int id, @RequestBody @Valid CoffeeDTO toUpdate) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        service.updateCoffee(id, toUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
       service.deleteCoffee(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    ResponseEntity<?> switchFavourite(@PathVariable int id) {
        service.switchFavourite(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PatchMapping("/{id}/{groupName}")
    ResponseEntity<?> addToGroup(@PathVariable int id, @PathVariable String groupName) {
        service.addToGroup(id, groupName);
        return ResponseEntity.ok().build();
    }
}
