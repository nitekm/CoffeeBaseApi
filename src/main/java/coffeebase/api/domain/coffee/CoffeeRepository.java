package coffeebase.api.domain.coffee;

import coffeebase.api.domain.coffee.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findAll();

    Optional<Coffee> findById(Integer id);

    Coffee save(Coffee coffee);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    @Query(value = "SELECT * FROM COFFEES c " +
            "WHERE UPPER(c.name) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.rating) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.origin) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.roaster) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.processing) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.roast_profile) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.region) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.continent) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.farm) LIKE UPPER(CONCAT('%',:content,'%'))" +
            "OR UPPER(c.crop_height) LIKE UPPER(CONCAT('%',:content,'%'))",
             nativeQuery = true)
    List<Coffee> findByFields(@Param("content") String content);
}