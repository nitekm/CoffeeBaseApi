package coffeebase.api.domain.user.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.file.CoffeeBaseFileRepository;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import coffeebase.api.domain.user.UserRepository;
import coffeebase.api.exceptions.exception.DeleteUnsuccessful;
import coffeebase.api.utils.SecurityContextHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserSettingsService {

    private final CoffeeRepository coffeeRepository;
    private final TagRepository tagRepository;
    private final CoffeeBaseFileRepository coffeeBaseFileRepository;
    private final CoffeeBaseFileService coffeeBaseFileService;
    private final BrewRepository brewRepository;
    private final UserRepository userRepository;

    public void deleteUserAccount() {
        var userId = SecurityContextHelper.getUserFromSecurityContext().getUserId();
        deleteCoffees(userId);
        deleteFiles(userId);
        deleteTags(userId);
        deleteBrews(userId);
        deleteUser(userId);
    }

    private void deleteTags(String userId) {
        try {
            tagRepository.deleteAllByCreatedByUserId(userId);
            log.info("Tags for user {} deleted", userId);
        } catch (Exception e) {
            throw new DeleteUnsuccessful();
        }
    }

    private void deleteFiles(String userId) {
        coffeeBaseFileRepository.findAllFileNamesByCreatedByUserId(userId)
                        .forEach(coffeeBaseFileService::delete);
        try {
            coffeeBaseFileRepository.deleteAllByCreatedByUserId(userId);
            log.info("Files for user {} deleted", userId);
        } catch (Exception e) {
            throw new DeleteUnsuccessful();
        }
    }

    private void deleteCoffees(String userId) {
        try {
            coffeeRepository.deleteAllByCreatedByUserId(userId);
            log.info("Coffees for user {} deleted", userId);
        } catch (Exception e) {
            throw new DeleteUnsuccessful();
        }
    }

    private void deleteBrews(String userId) {
        try {
            brewRepository.deleteAllByCreatedByUserId(userId);
            log.info("Brews for user {} deleted", userId);
        } catch (Exception e) {
            throw new DeleteUnsuccessful();
        }
    }

    private void deleteUser(String userId) {
        try {
            userRepository.deleteByUserId(userId);
            log.info("User {} deleted", userId);
        } catch (Exception e) {
            throw new DeleteUnsuccessful();
        }
    }
}
