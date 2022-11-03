package insurance.claims.demo.dto;

import insurance.claims.demo.common.VehicleColour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Enumerated;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRegistrationRequest {
    private long userID;
    private String registrationNumber;
    private String vinNumber;
    private BigInteger mileage;
    private double estimatedValue;
}
