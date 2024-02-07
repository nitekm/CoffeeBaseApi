package coffeebase.api.domain.coffee.service;

import coffeebase.api.aspect.accesscheck.AccessCheck;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.coffee.model.PageCoffeeRequest;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static coffeebase.api.domain.coffee.service.CoffeeRequestFilterProcessor.*;
import static coffeebase.api.utils.SecurityContextHelper.getUserFromSecurityContext;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final CoffeeBaseFileService coffeeBaseFileService;
    private final TagRepository tagRepository;
    private final CoffeeMapper coffeeMapper;

    public Page<CoffeeDTO> getAllCoffees(PageCoffeeRequest request) {
        var user = getUserFromSecurityContext();
        var pageRequest = PageRequest.of(
                request.pageNumber(),
                request.pageSize(),
                Sort.by(Sort.Direction.valueOf(request.sortDirection()),request.sortProperty()));
        if (shouldApplyFilters(request.filters())) {
            return coffeeRepository.filterByParamsAndCreatedByUserId(
                    user.getUserId(),
                            processFavourite(request.filters().get("favourite")),
                            processFilters("continent", request.filters().get("continent")),
                            processFilters("roastProfile", request.filters().get("roastProfile")),
                             pageRequest)
                    .map(coffeeMapper::coffeeToDTO);
        }
        return coffeeRepository.findAllByCreatedByUserId(user.getUserId(), pageRequest)
                .map(coffeeMapper::coffeeToDTO);
    }


    public List<CoffeeDTO> getAllCoffees() {
        var user = getUserFromSecurityContext();
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

    @AccessCheck
    public CoffeeDTO getCoffeeById(Long id) {
        log.debug("Getting coffee with id: " + id + " CALLED!");
        var coffee = coffeeRepository.findById(id)
                .map(coffeeMapper::coffeeToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));

        return coffee;
    }

    public CoffeeDTO addCoffee(CoffeeDTO source, MultipartFile image) {
        var coffee = coffeeMapper.dtoToCoffee(source);

        saveImage(coffee, image);

        tagRepository.saveAll(coffee.getTags());
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

    @AccessCheck
    public CoffeeDTO updateCoffee(Long id, CoffeeDTO update, MultipartFile image) {
        log.info("Updating coffee with id: " + id + " CALLED!");
        return coffeeRepository.findById(id)
                .map(coffee -> updateCoffee(coffee, update, image))
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
    }

    private CoffeeDTO updateCoffee(Coffee coffee, CoffeeDTO updated, MultipartFile image) {
        var updatedCoffee = coffeeMapper.dtoToCoffee(updated);
        updatedCoffee.setId(coffee.getId());
        updatedCoffee.setBrews(coffee.getBrews());

        tagRepository.saveAll(updatedCoffee.getTags());
        saveImage(updatedCoffee, image);

        return coffeeMapper.coffeeToDTO(coffeeRepository.save(updatedCoffee));
    }

    @AccessCheck
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

    @AccessCheck
    public void deleteCoffee(Long id) {
        coffeeRepository.findById(id)
                .map(coffee -> {
                    deleteRelated(coffee);
                    coffeeRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Coffee with given id not found"));
        log.info("Delete coffee with id: " + id + "CALLED!");
    }

    private List<CoffeeDTO> searchByContent(String content) {
        var user = getUserFromSecurityContext();
        log.debug("Searching by" + content + " CALLED!");
        return coffeeRepository.findByFields(content, user.getUserId())
                .stream()
                .map(coffeeMapper::dtoForCoffeeList)
                .collect(Collectors.toList());
    }

    private void deleteRelated(Coffee coffee) {
        Optional.ofNullable(coffee.getCoffeeBaseFile())
                .ifPresent(file -> coffeeBaseFileService.delete(file.getName()));
        coffee.getTags().stream()
                .filter(tag -> tag.getCoffees().size() == 1)
                .filter(tag -> Objects.equals(tag.getCoffees().get(0).getId(), coffee.getId()))
                .forEach(tagRepository::delete);
    }
}
