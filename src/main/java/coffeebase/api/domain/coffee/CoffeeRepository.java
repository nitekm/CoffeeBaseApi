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

    List<Coffee> findAllByCreatedByUserId(String userId);

    @Query(nativeQuery = true,
            value = """
                    select created_by_user_id from coffees c
                    where c.id = :id
                    """
    )
    Optional<String> findCreatedByUserIdById(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = """
                    select * from coffees c
                    where c.created_by_user_id = :userId
                    and upper(c.name) like upper(concat('%',:content,'%'))
                    or upper(c.rating) like upper(concat('%',:content,'%'))
                    or upper(c.origin) like upper(concat('%',:content,'%'))
                    or upper(c.roaster) like upper(concat('%',:content,'%'))
                    or upper(c.processing) like upper(concat('%',:content,'%'))
                    or upper(c.roast_profile) like upper(concat('%',:content,'%'))
                    or upper(c.region) like upper(concat('%',:content,'%'))
                    or upper(c.continent) like upper(concat('%',:content,'%'))
                    or upper(c.farm) like upper(concat('%',:content,'%'))
                    or upper(c.crop_height) like upper(concat('%',:content,'%'))
                    """
    )
    List<Coffee> findByFields(@Param("content") String content, @Param("userId") String userId);
}
