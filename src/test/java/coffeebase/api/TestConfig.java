package coffeebase.api;

import coffeebase.api.coffee.model.Coffee;
import coffeebase.api.coffee.repository.CoffeeRepository;
import coffeebase.api.coffeegroup.CoffeeGroup;
import coffeebase.api.coffeegroup.CoffeeGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    CoffeeRepository coffeeRepository() {
        return new CoffeeRepository() {
            private HashMap<Integer, Coffee> coffees = new HashMap<>();
            @Override
            public List<Coffee> findAll() {
                return new ArrayList<>(coffees.values());
            }

            @Override
            public List<Coffee> findAllByOrderByNameAsc() {
                return null;
            }

            @Override
            public List<Coffee> findAllByOrderByRatingAsc() {
                return null;
            }

            @Override
            public List<Coffee> findAllByOrderByRatingDesc() {
                return null;
            }

            @Override
            public List<Coffee> findAllByOrderByNameDesc() {
                return null;
            }

            @Override
            public Optional<Coffee> findById(final Integer id) {
                return Optional.ofNullable(coffees.get(id));
            }

            @Override
            public Coffee save(final Coffee coffee) {
                int key = coffees.size() + 1;
                try {
                    var field = Coffee.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(coffee, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                coffees.put(key, coffee);
                return coffees.get(key);
            }

            @Override
            public boolean existsById(final Integer id) {
                return coffees.containsKey(id);
            }

            @Override
            public void deleteById(final Integer id) {

            }
        };
    }

    @Bean
    @Primary
    @Profile("integration")
    CoffeeGroupRepository coffeeGroupRepository() {
        return new CoffeeGroupRepository() {
            private HashMap<Integer, CoffeeGroup> coffeeGroups = new HashMap<>();
            @Override
            public List<CoffeeGroup> findAll() {
                return new ArrayList<>(coffeeGroups.values());
            }

            @Override
            public Optional<CoffeeGroup> findById(final int id) {
                return Optional.ofNullable(coffeeGroups.get(id));
            }

            @Override
            public Optional<CoffeeGroup> findByName(final String name) {
                return Optional.ofNullable(coffeeGroups.get(name));
            }

            @Override
            public CoffeeGroup save(final CoffeeGroup coffeeGroup) {
                int key = coffeeGroups.size() + 1;
                try {
                    var field = CoffeeGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(coffeeGroup, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                coffeeGroups.put(key, coffeeGroup);
                return coffeeGroups.get(key);
            }

            @Override
            public void deleteById(final int id) {}

            @Override
            public boolean existsById(final int id) {
                return coffeeGroups.containsKey(id);
            }

            @Override
            public boolean existsByName(final String name) {
                return false;
            }
        };
    }
}
