package coffeebase.api.coffee;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.repository.CoffeeRepository;
import coffeebase.api.coffee.service.CoffeeService;
import coffeebase.api.coffeegroup.CoffeeGroup;
import coffeebase.api.coffeegroup.CoffeeGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoffeeServiceTest {

    @Test
    @DisplayName("When coffee with given id not found should throw IllegalArgumentException")
    void switchFavourite_noCoffeeWithIdExists() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, null);
        //when
        var exception = catchThrowable(() -> toTest.switchFavourite(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");
    }

    @Test
    @DisplayName("Coffee with given id should change favourite field on opposite")
    void switchFavourite_worksAsExpected() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        //and
        Coffee coffee = new Coffee();
        boolean beforeSwitch = coffee.isFavourite();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, null);
        //when
        toTest.switchFavourite(1);
        //then
        assertThat(coffee.isFavourite()).isEqualTo(!beforeSwitch);
    }
    @Test
    @DisplayName("When coffee with given id not found should throw IllegalArgumentException")
    void addToGroup_noCoffeeWithIdExists() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, null);
        //when
        var exception = catchThrowable(() -> toTest.addToGroup(1, null));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");
    }

    @Test
    @DisplayName("When coffee group with given name not found should throw IllegalArgumentException")
    void addToGroup_coffeeWithIdExists_noGroupWithNameExists() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        var mockCoffeeGroupRepository = mock(CoffeeGroupRepository.class);
        //and
        Coffee coffee = new Coffee();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //and
        when(mockCoffeeGroupRepository.findByName(anyString())).thenReturn(Optional.empty());
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, mockCoffeeGroupRepository);
        //when
        var exception = catchThrowable(() -> toTest.addToGroup(1, "groupName"));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("group does not");
    }

    @Test
    @DisplayName("Should throw IllegalStateException when coffee is already in the group of the same type")
    void addToGroup_coffeeWithIdExists_GroupWithNameExists_coffeeAlreadyInGroupOfTheSameType() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        var mockCoffeeGroupRepository = mock(CoffeeGroupRepository.class);
        //and
        Coffee coffee = new Coffee();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //and
        CoffeeGroup coffeeGroup = new CoffeeGroup();
        when(mockCoffeeGroupRepository.findByName(anyString())).thenReturn(Optional.of(coffeeGroup));
        //and
        Set<CoffeeGroup> dummySet = new HashSet<>();
        dummySet.add(coffeeGroup);
        coffee.setCoffeeGroups(dummySet);
        var dummySetTypes = dummySet
                .stream()
                .map(set -> set.getGroupType())
                .collect(Collectors.toList());
        coffeeGroup.setGroupType(dummySetTypes.get(anyInt()));
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, mockCoffeeGroupRepository);
        //when
        var exception = catchThrowable(() -> toTest.addToGroup(1, "groupName"));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot be in 2 groups");
    }

    @Test
    @DisplayName("Should add coffee to group when coffee id exists, coffee group exists and coffee is not in group of the same type")
    void addToGroup_worksAsExpected() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        var mockCoffeeGroupRepository = mock(CoffeeGroupRepository.class);
        //and
        Coffee coffee = new Coffee();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //and
        CoffeeGroup coffeeGroup = new CoffeeGroup();
        when(mockCoffeeGroupRepository.findByName(anyString())).thenReturn(Optional.of(coffeeGroup));
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, mockCoffeeGroupRepository);
        //when
        toTest.addToGroup(1, "groupName");
        //then
        assertThat(coffee.getCoffeeGroups().contains(coffeeGroup));
    }

    @Test
    @DisplayName("deleteCoffeeFromGroup should throw IllegalArgumentException when coffee does not belong to given group")
    void deleteCoffeeFromGroup_coffeeDoesNotBelongToGivenGroup() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        var mockCoffeeGroupRepository = mock(CoffeeGroupRepository.class);
        //and
        Coffee coffee = new Coffee();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //and
        CoffeeGroup coffeeGroup = new CoffeeGroup();
        when(mockCoffeeGroupRepository.findByName(anyString())).thenReturn(Optional.of(coffeeGroup));
        //and
        var dummySet = new HashSet<Coffee>();
        dummySet.add(new Coffee());
        coffeeGroup.setCoffees(dummySet);
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, mockCoffeeGroupRepository);
        //when
        var exception = catchThrowable(() -> toTest.deleteCoffeeFromGroup(1, "group"));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not member of given group");
    }

    @Test
    @DisplayName("Should delete coffee from given group")
    void deleteCoffeeFromGroup_workAsExpected() {
        //given
        var mockCoffeeRepository = mock(CoffeeRepository.class);
        var mockCoffeeGroupRepository = mock(CoffeeGroupRepository.class);
        //and
        Coffee coffee = new Coffee();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));
        //and
        CoffeeGroup coffeeGroup = new CoffeeGroup();
        when(mockCoffeeGroupRepository.findByName(anyString())).thenReturn(Optional.of(coffeeGroup));
        //and
        var dummySet = new HashSet<Coffee>();
        dummySet.add(coffee);
        coffeeGroup.setCoffees(dummySet);
        //System under test
        var toTest = new CoffeeService(mockCoffeeRepository, mockCoffeeGroupRepository);
        //when
        toTest.deleteCoffeeFromGroup(1, "group");
        //then
        assertThat(!coffee.getCoffeeGroups().contains(coffeeGroup));

    }
}