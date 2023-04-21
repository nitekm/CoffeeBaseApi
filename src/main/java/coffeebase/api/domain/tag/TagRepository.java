package coffeebase.api.domain.tag;

import coffeebase.api.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByCreatedByUserId(String userId);

    @Query(nativeQuery = true,
            value = "select * from tags t " +
                    "where t.created_by_user_id = :userId " +
                    "and upper(t.name) like upper(concat('%',:name,'%'))")
    List<Tag> findByName(@Param("name") String name, @Param("userId") String userId);
}
