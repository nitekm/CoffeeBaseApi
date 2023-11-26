package coffeebase.api.domain.brew.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.model.BrewStatus;
import coffeebase.api.domain.brew.model.PourOver;
import coffeebase.api.exceptions.exception.BrewUpdateInterrupted;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrewStepService {

    private final BrewRepository brewRepository;
    private final BrewMapper brewMapper;

    public BrewDTO init(BrewDTO brewDTO) {
        if (brewDTO.id() == null) {
            return createNewBrew();
        }

        return brewRepository.findById(brewDTO.id())
                .map(brewMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Brew with given id not found"));
    }

    public BrewDTO finish(BrewDTO update) {
        return brewRepository.findById(update.id())
                .map(brew -> updateBrew(brew, update))
                .map(brewMapper::toDTO)
                .orElseThrow(BrewUpdateInterrupted::new);
    }

    private BrewDTO createNewBrew() {
        final var brew = new Brew();
        brew.setStatus(BrewStatus.STARTED);
        var newBrew = brewRepository.save(brew);
        return brewMapper.toDTO(newBrew);
    }

    private Brew updateBrew(Brew brew, BrewDTO update) {
        var updatedBrew = brewMapper.toEntityOmitId(update);
        updatedBrew.setId(brew.getId());
        updatedBrew = calculateTotalTimeOnFinish(updatedBrew);
        return brewRepository.save(updatedBrew);
    }

    private Brew calculateTotalTimeOnFinish(Brew updatedBrew) {
        if (updatedBrew.getStatus() != BrewStatus.COMPLETED) {
            return updatedBrew;
        }
        var totalTime = updatedBrew.getPourOvers().stream()
                .map(PourOver::getTime)
                .mapToInt(Integer::intValue)
                .sum();
        updatedBrew.setTotalTime(totalTime);
        return updatedBrew;
    }
}
