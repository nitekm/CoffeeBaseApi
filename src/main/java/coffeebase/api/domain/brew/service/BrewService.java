package coffeebase.api.domain.brew.service;

import coffeebase.api.aspect.accesscheck.AccessCheck;
import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.brew.model.BrewDTO;
import coffeebase.api.domain.brew.model.BrewStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static coffeebase.api.utils.SecurityContextHelper.getUserFromSecurityContext;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewService {

    final private BrewRepository brewRepository;
    final private BrewMapper brewMapper;

    public List<BrewDTO> getAllBrews() {
        var user = getUserFromSecurityContext();
        log.debug("Getting all brews for user" + user.getUserId() + " CALLED!");

        return brewRepository.findAllByCreatedByUserIdAndStatus(user.getUserId(), BrewStatus.COMPLETED)
                .stream()
                .map(brewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BrewDTO getBrewById(Long id) {
        log.debug("Getting brew with id: " + id + " CALLED!");
        var brew = brewRepository.findById(id)
                .map(brewMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));

        return brew;
    }
}
