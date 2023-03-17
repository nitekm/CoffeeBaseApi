package coffeebase.api.domain.coffee;

import coffeebase.api.domain.coffee.model.Coffee;
import coffeebase.api.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findAllByUser(User user);

    Optional<Coffee> findById(Integer id);

    Coffee save(Coffee coffee);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    @Query(nativeQuery = true,
            value = "select * from coffees c " +
                    "where c.user_id = :userId " +
                    "and upper(c.name) like upper(concat('%',:content,'%')) " +
                    "or upper(c.rating) like upper(concat('%',:content,'%')) " +
                    "or upper(c.origin) like upper(concat('%',:content,'%')) " +
                    "or upper(c.roaster) like upper(concat('%',:content,'%')) " +
                    "or upper(c.processing) like upper(concat('%',:content,'%')) " +
                    "or upper(c.roast_profile) like upper(concat('%',:content,'%')) " +
                    "or upper(c.region) like upper(concat('%',:content,'%')) " +
                    "or upper(c.continent) like upper(concat('%',:content,'%')) " +
                    "or upper(c.farm) like upper(concat('%',:content,'%')) " +
                    "or upper(c.crop_height) like upper(concat('%',:content,'%'))")
    List<Coffee> findByFields(@Param("content") String content, @Param("userId") Integer userId);
}
