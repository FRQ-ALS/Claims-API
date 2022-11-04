package insurance.claims.demo.repository;


import insurance.claims.demo.dto.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByUserIDAndVehicleID(long userID, long vehicleID);

    List<Quote> findAllByuserID(long userID);
}
