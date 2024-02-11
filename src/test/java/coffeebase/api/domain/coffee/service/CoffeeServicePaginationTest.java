package coffeebase.api.domain.coffee.service;

import coffeebase.api.domain.coffee.CoffeeRepository;
import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.domain.coffee.model.CoffeeDTO;
import coffeebase.api.domain.coffee.model.PageCoffeeRequest;
import coffeebase.api.domain.file.CoffeeBaseFileService;
import coffeebase.api.domain.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

import static coffeebase.api.authentication.AuthenticationHelper.setAuthenticationTestUser;
import static coffeebase.api.utils.TestCoffeeUtils.createCoffeeWithProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CoffeeServicePaginationTest {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeBaseFileService coffeeBaseFileService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CoffeeMapper coffeeMapper;
    @BeforeEach
    void setup() {
        setAuthenticationTestUser();
        //Dataset: 2 coffees with each continent and default value, 2 coffees with each favourite option, 2 coffees with
        // each roastProfile and default value. Every coffee with all values which can be sort property. Each string value
        // in coffee is same as name and number values go up according to alphabet letters.
        loadTestDataToDataBase();
    }

    @Test
    @DisplayName("Should return coffees sorted by id ascending")
    public void testGetAllCoffees_sortAscendingById() {
        var request = new PageCoffeeRequest(20, 0,
                "id", "ASC",
                new HashMap<>());

        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        List<Long> ids = result.getContent().stream().map(CoffeeDTO::id).collect(Collectors.toList());
        Assertions.assertEquals(ids, ids.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by id descending")
    public void testGetAllCoffees_sortDescendingById() {
        var request = new PageCoffeeRequest(20, 0, "id", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Long> ids = result.getContent().stream().map(CoffeeDTO::id).collect(Collectors.toList());
        Assertions.assertEquals(ids, ids.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by name ascending")
    public void testGetAllCoffees_sortAscendingByName() {
        var request = new PageCoffeeRequest(20, 0, "name", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> names = result.getContent().stream().map(CoffeeDTO::name).collect(Collectors.toList());
        Assertions.assertEquals(names, names.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by name descending")
    public void testGetAllCoffees_sortDescendingByName() {
        var request = new PageCoffeeRequest(20, 0, "name", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> names = result.getContent().stream().map(CoffeeDTO::name).collect(Collectors.toList());
        Assertions.assertEquals(names, names.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by rating ascending")
    public void testGetAllCoffees_sortAscendingByRating() {
        var request = new PageCoffeeRequest(20, 0,"rating", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Double> ratings = result.getContent().stream().map(CoffeeDTO::rating).collect(Collectors.toList());
        Assertions.assertEquals(ratings, ratings.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by rating descending")
    public void testGetAllCoffees_sortDescendingByRating() {
        var request = new PageCoffeeRequest(20, 0,"rating", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Double> ratings = result.getContent().stream().map(CoffeeDTO::rating).collect(Collectors.toList());
        Assertions.assertEquals(ratings, ratings.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by origin ascending")
    public void testGetAllCoffees_sortAscendingByOrigin() {
        var request = new PageCoffeeRequest(20, 0,"origin", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> origins = result.getContent().stream().map(CoffeeDTO::origin).collect(Collectors.toList());
        Assertions.assertEquals(origins, origins.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by origin descending")
    public void testGetAllCoffees_sortDescendingByOrigin() {
        var request = new PageCoffeeRequest(20, 0,"origin", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> origins = result.getContent().stream().map(CoffeeDTO::origin).collect(Collectors.toList());
        Assertions.assertEquals(origins, origins.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by roaster ascending")
    public void testGetAllCoffees_sortAscendingByRoaster() {
        var request = new PageCoffeeRequest(20, 0,"roaster", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> roasters = result.getContent().stream().map(CoffeeDTO::roaster).collect(Collectors.toList());
        Assertions.assertEquals(roasters, roasters.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by roaster descending")
    public void testGetAllCoffees_sortDescendingByRoaster() {
        var request = new PageCoffeeRequest(20, 0,"roaster", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> roasters = result.getContent().stream().map(CoffeeDTO::roaster).collect(Collectors.toList());
        Assertions.assertEquals(roasters, roasters.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by processing ascending")
    public void testGetAllCoffees_sortAscendingByProcessing() {
        var request = new PageCoffeeRequest(20, 0,"processing", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> processings = result.getContent().stream().map(CoffeeDTO::processing).collect(Collectors.toList());
        Assertions.assertEquals(processings, processings.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by processing descending")
    public void testGetAllCoffees_sortDescendingByProcessing() {
        var request = new PageCoffeeRequest(20, 0,"processing", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> processings = result.getContent().stream().map(CoffeeDTO::processing).collect(Collectors.toList());
        Assertions.assertEquals(processings, processings.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by region ascending")
    public void testGetAllCoffees_sortAscendingByRegion() {
        var request = new PageCoffeeRequest(20, 0,"region", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> regions = result.getContent().stream().map(CoffeeDTO::region).collect(Collectors.toList());
        Assertions.assertEquals(regions, regions.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by region descending")
    public void testGetAllCoffees_sortDescendingByRegion() {
        var request = new PageCoffeeRequest(20, 0,"region", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> regions = result.getContent().stream().map(CoffeeDTO::region).collect(Collectors.toList());
        Assertions.assertEquals(regions, regions.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by farm ascending")
    public void testGetAllCoffees_sortAscendingByFarm() {
        var request = new PageCoffeeRequest(20, 0,"farm", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> farms = result.getContent().stream().map(CoffeeDTO::farm).collect(Collectors.toList());
        Assertions.assertEquals(farms, farms.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by farm descending")
    public void testGetAllCoffees_sortDescendingByFarm() {
        var request = new PageCoffeeRequest(20, 0,"farm", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<String> farms = result.getContent().stream().map(CoffeeDTO::farm).collect(Collectors.toList());
        Assertions.assertEquals(farms, farms.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by cropHeight ascending")
    public void testGetAllCoffees_sortAscendingByCropHeight() {
        var request = new PageCoffeeRequest(20, 0,"cropHeight", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Integer> cropHeights = result.getContent().stream().map(CoffeeDTO::cropHeight).collect(Collectors.toList());
        Assertions.assertEquals(cropHeights, cropHeights.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by cropHeight descending")
    public void testGetAllCoffees_sortDescendingByCropHeight() {
        var request = new PageCoffeeRequest(20, 0,"cropHeight", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Integer> cropHeights = result.getContent().stream().map(CoffeeDTO::cropHeight).collect(Collectors.toList());
        Assertions.assertEquals(cropHeights, cropHeights.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by scaRating ascending")
    public void testGetAllCoffees_sortAscendingByScaRating() {
        var request = new PageCoffeeRequest(20, 0,"scaRating", "ASC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Integer> scaRatings = result.getContent().stream().map(CoffeeDTO::scaRating).collect(Collectors.toList());
        Assertions.assertEquals(scaRatings, scaRatings.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return coffees sorted by scaRating descending")
    public void testGetAllCoffees_sortDescendingByScaRating() {
        var request = new PageCoffeeRequest(20, 0,"scaRating", "DESC", new HashMap<>());
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);
        List<Integer> scaRatings = result.getContent().stream().map(CoffeeDTO::scaRating).collect(Collectors.toList());
        Assertions.assertEquals(scaRatings, scaRatings.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Should return only coffees with continent = Asia")
    public void testGetAllCoffees_filterByContinentAsia() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("continent", Collections.singleton("Asia"));
        PageCoffeeRequest request = new PageCoffeeRequest(20, 0, "id",
                "ASC", filters);

        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "Asia".equals(dto.continent())));
    }

    @Test
    @DisplayName("Should return only coffees with continent = Africa")
    public void testGetAllCoffees_filterByContinentAfrica() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("continent", Collections.singleton("Africa"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "Africa".equals(dto.continent())));
    }

    @Test
    @DisplayName("Should return only coffees with continent = South America")
    public void testGetAllCoffees_filterByContinentSouthAmerica() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("continent", Collections.singleton("South America"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "South America".equals(dto.continent())));
    }

    @Test
    @DisplayName("Should return only coffees which are favourited")
    public void testGetAllCoffees_filterByFavourites() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("favourite", Collections.singleton("true"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(CoffeeDTO::favourite));
    }

    @Test
    @DisplayName("Should return only coffees which are not favourited")
    public void testGetAllCoffees_filterByNonFavourites() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("favourite", Collections.singleton("false"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().noneMatch(CoffeeDTO::favourite));
    }

    @Test
    @DisplayName("Should return only coffees with roastProfile = Light")
    public void testGetAllCoffees_filterByRoastProfileLight() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("roastProfile", Collections.singleton("Light"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "Light".equals(dto.roastProfile())));
    }

    @Test
    @DisplayName("Should return only coffees with roastProfile = Dark")
    public void testGetAllCoffees_filterByRoastProfileDark() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("roastProfile", Collections.singleton("Dark"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "Dark".equals(dto.roastProfile())));
    }

    @Test
    @DisplayName("Should return only coffees with roastProfile = Omniroast")
    public void testGetAllCoffees_filterByRoastProfileOmniroast() {
        Map<String, Set<String>> filters = new HashMap<>();
        filters.put("roastProfile", Collections.singleton("Omniroast"));

        var request = new PageCoffeeRequest(20, 0, "id", "ASC", filters);
        Page<CoffeeDTO> result = coffeeService.getAllCoffees(request);

        assertTrue(result.getContent().stream().allMatch(dto -> "Omniroast".equals(dto.roastProfile())));
    }


    private void loadTestDataToDataBase() {
        List<Coffee> testCoffeeList = new ArrayList<>();

        Coffee coffee1 = createCoffeeWithProperties("a", 0.0, true, "a", "a", "a", "Light", "a", "Africa", "a", 0, 0);
        Coffee coffee2 = createCoffeeWithProperties("b", 0.5, false, "b", "b", "b", "Dark", "b", "Africa", "b", 100, 10);
        Coffee coffee3 = createCoffeeWithProperties("c", 1.0, true, "c", "c", "c", "Omniroast", "c", "Asia", "c", 200, 20);
        Coffee coffee4 = createCoffeeWithProperties("d", 1.5, false, "d", "d", "d", "RoastProfile", "d", "Asia", "d", 300, 30);
        Coffee coffee5 = createCoffeeWithProperties("e", 2.0, true, "e", "e", "e", "Light", "e", "South America", "e", 400, 40);
        Coffee coffee6 = createCoffeeWithProperties("f", 2.5, false, "f", "f", "f", "Dark", "f", "South America", "f", 500, 50);
        Coffee coffee7 = createCoffeeWithProperties("g", 3.0, true, "g", "g", "g", "Omniroast", "g", "Continent", "g", 600, 60);
        Coffee coffee8 = createCoffeeWithProperties("h", 3.5, false, "h", "h", "h", "RoastProfile", "h", "Continent", "h", 700, 70);
        Coffee coffee9 = createCoffeeWithProperties("i", 4.0, true, "i", "i", "i", "Light", "i", "Africa", "i", 800, 80);
        Coffee coffee10 = createCoffeeWithProperties("j", 4.5, false, "j", "j", "j", "Dark", "j", "Africa", "j", 900, 90);
        Coffee coffee11 = createCoffeeWithProperties("k", 5.0, true, "k", "k", "k", "Omniroast", "k", "Asia", "k", 1000, 100);

        testCoffeeList.add(coffee1);
        testCoffeeList.add(coffee2);
        testCoffeeList.add(coffee3);
        testCoffeeList.add(coffee4);
        testCoffeeList.add(coffee5);
        testCoffeeList.add(coffee6);
        testCoffeeList.add(coffee7);
        testCoffeeList.add(coffee8);
        testCoffeeList.add(coffee9);
        testCoffeeList.add(coffee10);
        testCoffeeList.add(coffee11);

        coffeeRepository.saveAll(testCoffeeList);
    }
}
