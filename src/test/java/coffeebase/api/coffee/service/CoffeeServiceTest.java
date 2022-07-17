package coffeebase.api.coffee.service;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.model.CoffeeMapper;
import coffeebase.api.coffee.repository.CoffeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest {

    @Mock
    private CoffeeRepository mockCoffeeRepository;

    @Mock
    private CoffeeMapper coffeeMapper;

    @InjectMocks
    private CoffeeService coffeeService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when switchFavourite called on non existent coffee id")
    void givenNoCoffeeExists_whenSwitchFavourite_thenThrowException() {
        //given
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        var exception = catchThrowable(() -> coffeeService.switchFavourite(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");
    }

    @Test
    @DisplayName("Coffee with given id should change favourite field on opposite")
    void givenCoffee_whenSwitchFavourite_thenFavouriteIsChanged() {
        //given
        Coffee coffee = new Coffee();
        boolean beforeSwitch = coffee.isFavourite();
        when(mockCoffeeRepository.findById(anyInt())).thenReturn(Optional.of(coffee));

        //when
        coffeeService.switchFavourite(1);

        //then
        assertThat(coffee.isFavourite()).isEqualTo(!beforeSwitch);
    }
}