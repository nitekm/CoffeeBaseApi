package coffeebase.api.domain.coffee;

import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.coffee.service.CoffeeService;
import coffeebase.api.exceptions.processing.IllegalExceptionProcessing;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/coffees")
@Transactional
@RequiredArgsConstructor
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping
    public ResponseEntity<List<CoffeeDTO>> getAllCoffees() {
        return ResponseEntity.ok(coffeeService.getAllCoffees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable Long id) {
        return ResponseEntity.ok(coffeeService.getCoffeeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CoffeeDTO>> searchByFields(@RequestParam String content) {
        return ResponseEntity.ok(coffeeService.search(content));
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@RequestPart("coffee") @Valid CoffeeDTO coffee,
                                               @RequestPart(value = "image", required = false) MultipartFile file) {
        var result = coffeeService.addCoffee(coffee, file);
        return ResponseEntity.created(URI.create("/")).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoffeeDTO> updateCoffee(@PathVariable Long id,
                                                  @RequestPart("coffee") @Valid CoffeeDTO toUpdate,
                                                  @RequestPart(value = "image", required = false) MultipartFile file) {
        return ResponseEntity.ok(coffeeService.updateCoffee(id, toUpdate, file));
    }

    @PutMapping("/add-brew/{brewId}")
    public ResponseEntity<CoffeeDTO> addBrewToCoffee(@PathVariable Long brewId, @RequestBody CoffeeDTO coffeeDTO) {
        return ResponseEntity.ok(coffeeService.addBrewToCoffee(coffeeDTO, brewId));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CoffeeDTO> switchFavourite(@PathVariable Long id) {
        return ResponseEntity.ok(coffeeService.switchFavourite(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
        return ResponseEntity.noContent().build();
    }
}
