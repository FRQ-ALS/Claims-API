package insurance.claims.demo.repository;


import insurance.claims.demo.dto.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByUserIDAndVehicleID(long userID, long vehicleID);
    List<Quote> findAllByuserIDAndUserAcceptance(long userID, boolean acceptance);

    List<Quote> findAllByuserID(long userID);

    @Modifying
    @Transactional
    @Query("UPDATE Quote q SET q.userAcceptance = TRUE WHERE q.quoteID = ?1")
    int setUserAcceptance(long quoteID);
}
