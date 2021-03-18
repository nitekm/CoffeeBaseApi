package coffeebase.api.coffeegroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlCoffeeGroupRepository extends CoffeeGroupRepository, JpaRepository<CoffeeGroup, Integer> {

    /*
    //TODO: FIX: With query to eliminate n+1 select problem findAll in Coffee Groups returning empty list
    @Override
    @Query("select g from CoffeeGroup g join fetch g.coffees")
    List<CoffeeGroup> findAll();
    */
}
