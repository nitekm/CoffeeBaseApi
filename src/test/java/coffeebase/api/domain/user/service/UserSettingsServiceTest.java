package coffeebase.api.domain.user.service;

import coffeebase.api.domain.brew.BrewRepository;
import coffeebase.api.domain.brew.model.Brew;
import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.file.CoffeeBaseFileRepository;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import coffeebase.api.domain.user.UserRepository;
import coffeebase.api.domain.user.model.Subscription;
import coffeebase.api.domain.user.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;

import static coffeebase.api.authentication.AuthenticationHelper.getUserId;
import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.createRandomCoffee;
import static coffeebase.api.utils.TestTagUtils.createRandomTag;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserSettingsServiceTest {

    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CoffeeBaseFileRepository coffeeBaseFileRepository;
    @Autowired
    private CoffeeBaseFileService coffeeBaseFileService;
    @Autowired
    private BrewRepository brewRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
        createCoffeeWithTagsAndFile();
        createUser();
    }

    @Test
    @DisplayName("Should delete all user data")
    void test_delete_user_account_ok() {
        //given setup

        //when
        userSettingsService.deleteUserAccount();

        //then
        assertAll(
                () -> assertTrue(coffeeRepository.findAll().isEmpty()),
                () -> assertTrue(tagRepository.findAll().isEmpty()),
                () -> assertTrue(brewRepository.findAll().isEmpty()),
                () -> assertTrue(userRepository.findAll().isEmpty())
        );
    }

    private Coffee createCoffeeWithTagsAndFile() {
        var tags = Arrays.asList(createRandomTag(), createRandomTag(), createRandomTag(), createRandomTag());
        tagRepository.saveAll(tags);
        var brew = new Brew();
        brewRepository.save(brew);
        brew.setCreatedByUserId(getUserId());
        var coffee = createRandomCoffee("test");
        coffee.setTags(tags);
        coffee.setBrews(Collections.singletonList(brew));

        return coffeeRepository.save(coffee);
    }

    private User createUser() {
        var user = new User();
        user.setUserId(getUserId());
        user.setUsername("test");
        var subscription = new Subscription();
        subscription.setActive(true);
        user.setSubscription(subscription);

        return userRepository.save(user);
    }

}
