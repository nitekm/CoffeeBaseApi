package coffeebase.api.domain.brew.controller;

import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.service.BrewStepService;
import coffeebase.api.exceptions.processing.IllegalExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/brews/step/")
@RequiredArgsConstructor
public class BrewStepController {

    private final BrewStepService brewStepService;

    @RequestMapping("init")
    public ResponseEntity<BrewDTO> init(BrewDTO brewDTO) {
        return ResponseEntity.ok(brewStepService.init(brewDTO));
    }

    @RequestMapping("finish")
    public ResponseEntity<BrewDTO> finish(BrewDTO brewDTO) {
        return ResponseEntity.ok(brewStepService.finish(brewDTO));
    }
}
