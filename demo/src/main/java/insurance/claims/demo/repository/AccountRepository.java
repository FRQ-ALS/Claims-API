package insurance.claims.demo.repository;

import insurance.claims.demo.dto.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE AppUser a SET a.registeredCar = TRUE WHERE a.userID =?1")
    void setCarRegistryToTrue(long userID);

//    update app_user set registered_car = true where userid =?1

    //UPDATE AppUser u SET u.registeredCar = TRUE WHERE u.userID = ?1
}
