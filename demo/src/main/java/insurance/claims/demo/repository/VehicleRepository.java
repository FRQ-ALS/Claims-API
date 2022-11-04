package insurance.claims.demo.repository;

import insurance.claims.demo.dto.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByUserIDAndRegistrationNumber(long userID, String registrationNumber);
    Optional<Vehicle> findByUserID(long userID);

    @Modifying
    @Transactional
    @Query("UPDATE Vehicle v SET v.isInsured = TRUE WHERE v.vehicleID = ?1")
    int setCarInsuredToTrue(long vehicleID);

}
