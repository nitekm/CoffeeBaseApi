package coffeebase.api.domain.coffee;

import coffeebase.api.domain.coffee.model.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findAllByCreatedByUserId(String userId);

    Page<Coffee> findAllByCreatedByUserId(String userId, Pageable pageable);

    @Query(
       """
       SELECT c
       FROM Coffee c 
       WHERE c.createdByUserId = :userId
       AND (c.favourite IN :favourites)
       AND (c.continent IN :continents)
       AND (c.roastProfile IN :roastProfiles)
       """)
    Page<Coffee> filterByParamsAndCreatedByUserId(
            @Param("userId") String userId,
            @Param("favourites") List<Boolean> favourites,
            @Param("continents") Set<String> continents,
            @Param("roastProfiles") Set<String> roastProfiles,
            Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    select created_by_user_id from coffees c
                    where c.id = :id
                    """
    )
    Optional<String> findCreatedByUserIdById(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = """
                    select distinct 
                        c.id,
                        c.created_at,
                        c.created_by_user_id,
                        c.updated_at,
                        c.continent,
                        c.crop_height,
                        c.farm,                
                        c.favourite,          
                        c.name,              
                        c.origin,            
                        c.processing,        
                        c.rating,             
                        c.region,             
                        c.roast_profile,      
                        c.roaster,            
                        c.sca_rating,          
                        c.file_id           
                    from coffees c
                    left join coffee_tag ct on c.id = ct.coffee_id
                    left join tags t on ct.tag_id = t.id
                    where c.created_by_user_id = :userId
                    and (
                    upper(c.name) like upper(concat('%',:content,'%'))
                    or upper(c.rating) like upper(concat('%',:content,'%'))
                    or upper(c.origin) like upper(concat('%',:content,'%'))
                    or upper(c.roaster) like upper(concat('%',:content,'%'))
                    or upper(c.processing) like upper(concat('%',:content,'%'))
                    or upper(c.roast_profile) like upper(concat('%',:content,'%'))
                    or upper(c.region) like upper(concat('%',:content,'%'))
                    or upper(c.continent) like upper(concat('%',:content,'%'))
                    or upper(c.farm) like upper(concat('%',:content,'%'))
                    or upper(c.crop_height) like upper(concat('%',:content,'%'))
                    or upper(t.name) like upper(concat('%',:content,'%'))
                    )
                    """
    )
    List<Coffee> findByFields(@Param("content") String content, @Param("userId") String userId);

    void deleteAllByCreatedByUserId(String userId);
}
