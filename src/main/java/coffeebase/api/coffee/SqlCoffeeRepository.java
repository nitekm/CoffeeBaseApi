package coffeebase.api.coffee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlCoffeeRepository extends CoffeeRepository, JpaRepository<Coffee, Integer> {

}
