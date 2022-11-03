package insurance.claims.demo.service;


import insurance.claims.demo.dto.ClaimRequest;
import insurance.claims.demo.dto.Claim;
import insurance.claims.demo.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public ResponseEntity<?> findByVin(ClaimRequest claimRequest) {
        Optional<Claim> claim = claimRepository
                .findByVINAndInsuranceNo(claimRequest.getVin(), claimRequest.getInsuranceNo());

        if (claim.isPresent()) return ResponseEntity.ok(claim.get());

        if (claim.isEmpty()) return ResponseEntity.ok(new Claim());

        return ResponseEntity.ok("Unknown Error");
    }

    public void generateClaims() {

        Claim claim = new Claim(
                "12345",
                54321L,
                23990,
                "Ford",
                "Focus",
                2017
        );
        claimRepository.save(claim);

        Claim claim2 = new Claim(
                "00000",
                99999L,
                10500,
                "Mercedes",
                "E-Class",
                2019
        );

        claimRepository.save(claim2);

    }
}
