package coffeebase.api.coffeegroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface SqlCoffeeGroupRepository extends CoffeeGroupRepository, JpaRepository<CoffeeGroup, Integer> {

    /*
    //TODO: FIX: With query to eliminate n+1 selects problem findAll in Coffee Groups returning empty list
    @Override
    @Query("select g from CoffeeGroup g join fetch g.coffees")
    List<CoffeeGroup> findAll();
    */
}
