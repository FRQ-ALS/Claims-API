package insurance.claims.demo.controller;

import insurance.claims.demo.dto.Vehicle;
import insurance.claims.demo.dto.VehicleRegistrationRequest;
import insurance.claims.demo.service.VehicleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    //just another way of doing it
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //endpoint for user vehicle registration

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerVehicle
            (@RequestBody VehicleRegistrationRequest vehicleRegistrationRequest) {

        return ResponseEntity.ok(vehicleService.registerVehicle(vehicleRegistrationRequest));
    }

    @GetMapping(path = "/get/{userid}")

    public ResponseEntity<?> getVehicle(@PathVariable("userid") long userID){
        return ResponseEntity.ok(vehicleService.getVehicle(userID));

    }
}
