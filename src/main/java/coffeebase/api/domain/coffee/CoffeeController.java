package coffeebase.api.domain.coffee;

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
@Transactional
public class CoffeeController {

    private final CoffeeService coffeeService;
    public CoffeeController(final CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeDTO>> getAllCoffees() {
        return ResponseEntity.ok(coffeeService.getAllCoffees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable int id) {
        return ResponseEntity.ok(coffeeService.getCoffeeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CoffeeDTO>> searchByFields(@RequestParam String content) {
        return ResponseEntity.ok(coffeeService.search(content));
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@RequestBody @Valid CoffeeDTO coffee) {
        var result = coffeeService.addCoffee(coffee);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoffeeDTO> updateCoffee(@PathVariable int id, @RequestBody @Valid CoffeeDTO toUpdate) {
        return ResponseEntity.ok(coffeeService.updateCoffee(id, toUpdate));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CoffeeDTO> switchFavourite(@PathVariable int id) {
        return ResponseEntity.ok(coffeeService.switchFavourite(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        coffeeService.deleteCoffee(id);
        return ResponseEntity.noContent().build();
    }
}
