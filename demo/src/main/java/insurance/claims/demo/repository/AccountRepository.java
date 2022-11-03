package insurance.claims.demo.repository;

import insurance.claims.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
