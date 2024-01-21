package coffeebase.api.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeBaseFileRepository extends JpaRepository<CoffeeBaseFile, Integer> {
    void deleteAllByCreatedByUserId(String userId);

    @Query(nativeQuery = true,
    value = """
            select f.name 
            from files f
            where f.created_by_user_id = :userId
            """)
    List<String> findAllFileNamesByCreatedByUserId(@Param("userId") String userId);
}
