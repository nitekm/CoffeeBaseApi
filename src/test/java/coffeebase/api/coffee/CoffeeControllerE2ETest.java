package coffeebase.api.coffee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoffeeControllerE2ETest {
        @LocalServerPort
        private int port;

        @Autowired
        TestRestTemplate restTemplate;

        @Autowired
        CoffeeRepository coffeeRepository;

        @Test
        void get_ReturnsAllCoffees() {
            //given
            int initSize = coffeeRepository.findAll().size();
            coffeeRepository.save(new Coffee("coffoo", "foo", "foo", 5, ""));
            coffeeRepository.save(new Coffee("coffbar", "bar", "bar", 1, ""));

            //when
            Coffee[] result = restTemplate.getForObject("http://localhost:" + port + "/coffees", Coffee[].class);
            //then
            assertThat(result).hasSize(initSize+2);
        }
    }

