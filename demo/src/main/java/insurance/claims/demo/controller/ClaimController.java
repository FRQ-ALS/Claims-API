package insurance.claims.demo.controller;

import insurance.claims.demo.dto.ClaimRequest;
import insurance.claims.demo.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;
    @PostMapping(value = "/make")
    public ResponseEntity<?> makeClaim(@RequestBody ClaimRequest request) {
        return claimService.findByVin(request);
    }

    @PostMapping(path = "/generate")
    public ResponseEntity<?> generateUsers() {
        claimService.generateClaims();
        return ResponseEntity.ok("Claims have been generated");

    }
}
