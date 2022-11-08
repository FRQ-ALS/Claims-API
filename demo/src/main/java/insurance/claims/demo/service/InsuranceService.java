package insurance.claims.demo.service;

import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.ClaimRepository;
import insurance.claims.demo.repository.InsuranceRepository;
import insurance.claims.demo.repository.VehicleRepository;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.FilterableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ClaimRepository claimRepository;


    public List<InsuranceDTO> getInsuranceForUser(long userID) {

        List<Insurance> insurances = insuranceRepository.findByUserID(userID);

        List<InsuranceDTO> result = new ArrayList<>();

        //looping through insurances to create DTO objects
        insurances.forEach(insurance -> {

            Vehicle vehicle = vehicleRepository.findById(insurance.getVehicleID()).get();
            //adding leading zeroes to insurance ID until it reaches 5 characters
            String formattedInsuranceID = String.format("%05d", insurance.getInsuranceID());
            InsuranceDTO insuranceDTO = new InsuranceDTO(
                    formattedInsuranceID,
                    vehicle.getRegistrationNumber(),
                    insurance.getInsuranceExpiry()

            );

            result.add(insuranceDTO);

        });


        return result;

    }


    //method that generates a claim
    public ClaimResponse makeInsuranceClaim(ClaimRequest request) {

        //decimal formatter to set claim amount to 2 decimal places

        DecimalFormat df = new DecimalFormat("0.00");

        //checking if a claim already exists, in which case we just return that claim
        if (checkClaimExists((request.getInsuranceID()))) {
            Claim claim = claimRepository.
                    findByInsuranceNoAndExpired(
                            request.getInsuranceID(),
                            false).get();

            return new ClaimResponse(claim.getClaimAmount(), claim.getClaimExpiry());

        }

        double claimAmount = 0.0;

        Optional<Insurance> insurance = insuranceRepository.findById(request.getInsuranceID());

        if (insurance.isEmpty()) throw new IllegalStateException("Insurance not found");

        //creating placeholder claim amounts

        if (insurance.get().getPaymentType().equals("annually")) {
            claimAmount = insurance.get().getPaymentAmount() * 0.92;
        }

        if (insurance.get().getPaymentType().equals("monthly")) {
            claimAmount = (insurance.get().getPaymentAmount() * 12) * 0.60;
        }

        //generating new claim entity for records

        Claim claim = new Claim(
                request.getInsuranceID(),
                df.format(claimAmount),
                LocalDateTime.now().plusDays(5),
                request.getUserID()
        );

        claimRepository.save(claim);

        return new ClaimResponse(claim.getClaimAmount(), claim.getClaimExpiry());
    }


    //method to check whether a claim for a particular insurance has been made
    //to ensure no replicas are made

    public boolean checkClaimExists(long insuranceID) {

        Optional<Claim> claim = claimRepository.findByInsuranceNoAndExpired(insuranceID, false);

        if (claim.isEmpty()) {
            return false;
        }

        return true;

    }

}
