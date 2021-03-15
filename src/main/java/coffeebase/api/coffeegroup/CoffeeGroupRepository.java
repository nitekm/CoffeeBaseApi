package coffeebase.api.coffeegroup;

import java.util.List;
import java.util.Optional;

public interface CoffeeGroupRepository {
    List<CoffeeGroup> findAll();

    Optional<CoffeeGroup> findById(int id);

    Optional<CoffeeGroup> findByName(String name);

    CoffeeGroup save(CoffeeGroup coffeeGroup);
}
