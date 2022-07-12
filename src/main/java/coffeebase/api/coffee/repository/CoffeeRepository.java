package coffeebase.api.coffee.repository;

import coffeebase.api.coffee.model.Coffee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository {

    List<Coffee> findAll();

    List<Coffee> findAllByOrderByNameAsc();

    List<Coffee> findAllByOrderByRatingAsc();

    List<Coffee> findAllByOrderByRatingDesc();

    List<Coffee> findAllByOrderByNameDesc();

    Optional<Coffee> findById(Integer id);

    Coffee save(Coffee coffee);

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
