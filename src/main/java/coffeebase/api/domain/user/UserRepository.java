package coffeebase.api.domain.user;

import coffeebase.api.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User User);
    Optional<User> findByUserId(String userId);
    void deleteByUserId(String userId);
}
