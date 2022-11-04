package insurance.claims.demo.service;


import insurance.claims.demo.common.DVLARegApi;
import insurance.claims.demo.dto.Vehicle;
import insurance.claims.demo.dto.VehicleRegistrationRequest;
import insurance.claims.demo.dto.GenericResponse;
import insurance.claims.demo.repository.AccountRepository;
import insurance.claims.demo.repository.VehicleRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DVLARegApi dvlaRegApi;

    @Autowired
    private AccountRepository accountRepository;

    Logger log = LoggerFactory.getLogger(VehicleService.class);

    public GenericResponse registerVehicle(VehicleRegistrationRequest request) {
        log.info("Request ID : " + request.getUserID());
        //mapping json obtained from DVLA API onto vehicle instance

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = dvlaRegApi
                    .getVehicleDetails(request.getRegistrationNumber());
        } catch (IllegalStateException e) {
            log.info("Car with REG: " + request.getRegistrationNumber() + " not found");
            return new GenericResponse(false, e.getMessage());

        }

        Vehicle vehicle = new Vehicle(
                request.getUserID(),
                request.getRegistrationNumber(),
                jsonObject.getString("make"),
                jsonObject.getString("colour"),
                request.getVinNumber(),
                jsonObject.getInt("yearOfManufacture"),
                request.getEstimatedValue(),
                request.getMileage(),
                jsonObject.getString("fuelType")
        );

        vehicleRepository.save(vehicle);


        //verifies that vehicle was actually saved to DB
        if (verifyVehicleSave(request.getRegistrationNumber(), request.getUserID())) {
            log.info("Vehicle with registration: " + request.getRegistrationNumber()
                    + "Has been registered by user " + request.getUserID());

            //updating car registry status to true for user account after they add their car
            System.out.println(request.getUserID());
            accountRepository.setCarRegistryToTrue(request.getUserID());

            return new GenericResponse(true, "Your vehicle has been registered");
        }
        log.error("Error saving vehicle with reg number: " + request.getRegistrationNumber());

        //in case no conditions are hit, returning unknown error
        return new GenericResponse(false, "Your vehicle could not be saved");
    }

    //Method that returns current user vehicle
    public Vehicle getVehicle(long userID) {
        Optional<Vehicle> vehicle = vehicleRepository.findByUserID(userID);
        return vehicle.get();
    }


    //method that verifies whether vehicle has been saved to repository.
    private boolean verifyVehicleSave(String regNumber, long userID) {
        return vehicleRepository
                .findByUserIDAndRegistrationNumber(userID, regNumber)
                .isPresent();
    }

}
