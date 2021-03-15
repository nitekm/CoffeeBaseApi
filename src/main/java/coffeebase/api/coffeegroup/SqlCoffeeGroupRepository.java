package coffeebase.api.coffeegroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface SqlCoffeeGroupRepository extends CoffeeGroupRepository, JpaRepository<CoffeeGroup, Integer> {
    @Override
    List<CoffeeGroup> findAll();
}
