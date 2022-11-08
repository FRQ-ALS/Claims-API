package insurance.claims.demo.controller;

import insurance.claims.demo.dto.ClaimRequest;
import insurance.claims.demo.repository.ClaimRepository;
import insurance.claims.demo.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedStoredProcedureQuery;

@RestController
@RequestMapping(path = "/api/v1/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;


    @GetMapping("/getInsurance/{userID}")
    public ResponseEntity<?> getInsurance(@PathVariable("userID") long userID) {

        return ResponseEntity.ok(insuranceService.getInsuranceForUser(userID));
    }


    @PostMapping("/makeclaim")
    public ResponseEntity<?> makeClaim(@RequestBody ClaimRequest request) {

        return ResponseEntity.ok(insuranceService.makeInsuranceClaim(request));
    }



}
