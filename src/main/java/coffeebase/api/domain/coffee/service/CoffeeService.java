package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.security.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final CoffeeBaseFileService coffeeBaseFileService;
    private final CoffeeMapper coffeeMapper;

    public List<CoffeeDTO> getAllCoffees() {
        var user = getUserFromRequest();
        log.debug("Getting all coffees for user" + user.getUserId() + " CALLED!");

        return coffeeRepository.findAllByCreatedByUserId(user.getUserId())
                .stream()
                .map(coffeeMapper::dtoForCoffeeList)
                .collect(Collectors.toList());
    }

    public List<CoffeeDTO> search(String content) {
        if (content.isBlank()) {
            return getAllCoffees();
        }
        return searchByContent(content);
    }

    public CoffeeDTO getCoffeeById(Long id) {
        log.debug("Getting coffee with id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(coffeeMapper::coffeeToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    public CoffeeDTO addCoffee(CoffeeDTO source, MultipartFile image) {
        var coffee = coffeeMapper.dtoToCoffee(source);

        saveImage(coffee, image);

        var savedCoffee = coffeeRepository.save(coffee);
        log.info("Saved new coffee");

        return coffeeMapper.coffeeToDTO(savedCoffee);
    }

    private void saveImage(Coffee coffee, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return;
        }
        coffee.setCoffeeBaseFile(coffeeBaseFileService.save(image));
    }

    public CoffeeDTO updateCoffee(Long id, CoffeeDTO update, MultipartFile image) {
        log.info("Updating coffee with id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(coffee -> updateCoffee(coffee, update, image))
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    private CoffeeDTO updateCoffee(Coffee coffee, CoffeeDTO updated, MultipartFile image) {
        var updatedCoffee = coffeeMapper.dtoToCoffee(updated);
        updatedCoffee.setId(coffee.getId());

        saveImage(updatedCoffee, image);

        return coffeeMapper.coffeeToDTO(coffeeRepository.save(updatedCoffee));
    }

    public CoffeeDTO switchFavourite(Long id) {
        log.info("Switching favourites for coffee id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(this::switchFavouriteSaveAndMap)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    private CoffeeDTO switchFavouriteSaveAndMap(Coffee coffee) {
        coffee.setFavourite(!coffee.getFavourite());
        final var updatedCoffee = coffeeRepository.save(coffee);
        return coffeeMapper.coffeeToDTO(updatedCoffee);
    }

    public void deleteCoffee(Long id) {
        coffeeRepository.findById(id)
                .map(coffee -> {
                    coffeeRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        log.info("Delete coffee with id: " + id + "CALLED!");
    }

    private List<CoffeeDTO> searchByContent(String content) {
        var user = getUserFromRequest();
        log.debug("Searching by" + content + " CALLED!");
        return coffeeRepository.findByFields(content, user.getUserId())
                .stream()
                .map(coffeeMapper::dtoForCoffeeList)
                .collect(Collectors.toList());
    }

    private User getUserFromRequest() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
