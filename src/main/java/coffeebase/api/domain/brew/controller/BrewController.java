package coffeebase.api.domain.brew.controller;

import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.service.BrewService;
import coffeebase.api.exceptions.processing.IllegalExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/brews")
@RequiredArgsConstructor
public class BrewController {

    private final BrewService brewService;

    @GetMapping
    public ResponseEntity<List<BrewDTO>> getAllBrews() {
        return ResponseEntity.ok(brewService.getAllBrews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrewDTO> getBrewById(@PathVariable Long id) {
        return ResponseEntity.ok(brewService.getBrewById(id));
    }
}
