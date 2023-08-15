package coffeebase.api.domain.brew;

import coffeebase.api.domain.brew.model.Brew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrewRepository extends JpaRepository<Brew, Long> {
    List<Brew> findAllByCreatedByUserId(String userId);
}
