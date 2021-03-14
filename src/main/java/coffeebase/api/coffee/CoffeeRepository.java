package coffeebase.api.coffee;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CoffeeRepository {

    List<Coffee> findAll();

    Optional<Coffee> findById(Integer id);

    Coffee save(Coffee coffee);

    boolean existsById(Integer id);

    void deleteById(Integer id);

}
