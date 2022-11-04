package insurance.claims.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteParameters {
    private int driverAge;
    private int yearOfManufacture;
    private double estimatedValue;
    private String Colour;
    private String fuelType;
}
