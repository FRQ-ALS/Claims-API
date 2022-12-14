package insurance.claims.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimRequest {
    private long userID;
    private long insuranceID;
    private long vehicleID;
}
