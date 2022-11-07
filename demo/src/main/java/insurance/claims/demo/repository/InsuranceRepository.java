package insurance.claims.demo.repository;

import insurance.claims.demo.dto.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    List<Insurance> findByUserID(long userID);
}
