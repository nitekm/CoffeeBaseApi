package coffeebase.api.coffee.controller;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.model.CoffeeDTO;
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

    private CoffeeService coffeeService;

    public CoffeeController(final CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping()
    public ResponseEntity<List<Coffee>> getAllCoffees() {
        return ResponseEntity.ok(coffeeService.getAllCoffees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffeeById(@PathVariable int id) {
        return ResponseEntity.ok(coffeeService.getCoffeeById(id));
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@RequestBody @Valid CoffeeDTO coffee) {
        var result = coffeeService.addCoffee(coffee);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoffee(@PathVariable int id, @RequestBody @Valid CoffeeDTO toUpdate) {
        coffeeService.updateCoffee(id, toUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        coffeeService.deleteCoffee(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> switchFavourite(@PathVariable int id) {
        coffeeService.switchFavourite(id);
        return ResponseEntity.ok().build();
    }
}
