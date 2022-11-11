package coffeebase.api.domain.tag;

import coffeebase.api.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag save(Tag tag);
    boolean existsByName(String name);
    Optional<Tag> findById(Integer id);
    void deleteById(Integer id);

    @Query(value = "SELECT * FROM TAGS t " +
            "WHERE UPPER(t.name) LIKE UPPER(CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Tag> findByName(@Param("name") String name);
}
