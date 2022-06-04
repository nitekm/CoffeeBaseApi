package coffeebase.api.security.repository;

import coffeebase.api.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User User);
    User findByUserId(String userId);
    boolean existsByUserId(String userId);

}
