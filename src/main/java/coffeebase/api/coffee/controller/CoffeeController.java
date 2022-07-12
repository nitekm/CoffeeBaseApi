package coffeebase.api.coffee.controller;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.model.CoffeeDTO;
import coffeebase.api.coffee.repository.CoffeeRepository;
import coffeebase.api.coffee.service.CoffeeService;
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

    @GetMapping()
    ResponseEntity<List<Coffee>> getAllCoffees() {
        return ResponseEntity.ok(service.getAllCoffees());
    }

    @GetMapping("/{id}")
    ResponseEntity<Coffee> getCoffeeById(@PathVariable int id) {
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
}
