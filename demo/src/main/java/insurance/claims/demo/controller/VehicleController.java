package insurance.claims.demo.controller;

import insurance.claims.demo.dto.Vehicle;
import insurance.claims.demo.dto.VehicleRegistrationRequest;
import insurance.claims.demo.service.VehicleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/vehicles")
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

}
