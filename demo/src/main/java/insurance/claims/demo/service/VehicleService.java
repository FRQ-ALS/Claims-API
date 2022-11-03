package insurance.claims.demo.service;


import insurance.claims.demo.common.DVLARegApi;
import insurance.claims.demo.dto.Vehicle;
import insurance.claims.demo.dto.VehicleRegistrationRequest;
import insurance.claims.demo.dto.GenericResponse;
import insurance.claims.demo.repository.VehicleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DVLARegApi dvlaRegApi;

    public GenericResponse registerVehicle(VehicleRegistrationRequest request) {

        //mapping json obtained from API onto vehicle instance

        JSONObject jsonObject = dvlaRegApi
                .getVehicleDetails(request.getRegistrationNumber());
        Vehicle vehicle = new Vehicle(
                request.getUserID(),
                request.getRegistrationNumber(),
                jsonObject.getString("MAKE"),
                jsonObject.getString("colour"),
                request.getVinNumber(),
                jsonObject.getInt("yearOfManufacture"),
                request.getEstimatedValue(),
                request.getMileage(),
                jsonObject.getString("fuelType")
        );

        vehicleRepository.save(vehicle);

        return new GenericResponse(true, "Your vehicle has been registered");
    }

}
