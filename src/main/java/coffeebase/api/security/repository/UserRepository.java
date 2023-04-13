package coffeebase.api.security.repository;

import coffeebase.api.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User User);
    Optional<User> findByUserId(String userId);
}
