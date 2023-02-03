package coffeebase.api.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeBaseFileRepository extends JpaRepository<CoffeeBaseFile, Integer> {}
