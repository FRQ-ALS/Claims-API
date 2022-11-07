package insurance.claims.demo.repository;

import insurance.claims.demo.dto.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {


    Optional<Claim> findByInsuranceNoAndExpired(long insuranceNo, boolean expired);


}

