package coffeebase.api.domain.brew.controller;

import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.service.BrewStepService;
import coffeebase.api.exceptions.processing.ExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExceptionProcessing
@RequestMapping("/brews/step/")
@RequiredArgsConstructor
public class BrewStepController {

    private final BrewStepService brewStepService;

    @PostMapping("init")
    public ResponseEntity<BrewDTO> init(@RequestBody BrewDTO brewDTO) {
        return ResponseEntity.ok(brewStepService.init(brewDTO));
    }

    @PostMapping("finish")
    public ResponseEntity<BrewDTO> finish(@RequestBody BrewDTO brewDTO) {
        return ResponseEntity.ok(brewStepService.finish(brewDTO));
    }
}
