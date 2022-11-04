package insurance.claims.demo.dto;


import insurance.claims.demo.common.VehicleColour;
import insurance.claims.demo.controller.VehicleController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @SequenceGenerator(
            name = "quote_sequence",
            sequenceName = "quote_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quote_sequence"
    )
    private long vehicleID;
    private long userID;
    private String registrationNumber;
    private String make;
    private String model;
    private String colour;
    private String vinNumber;
    private int yearOfManufacture;
    private double estimatedValue;

    private BigInteger mileage;
    private String fuelType;

    private boolean isInsured;



    public Vehicle(long userID, String registrationNumber, String make,
                    String colour, String vinNumber,
                   int yearOfManufacture, double estimatedValue, BigInteger mileage, String fuelType) {
        this.userID = userID;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.colour = colour;
        this.vinNumber = vinNumber;
        this.yearOfManufacture = yearOfManufacture;
        this.estimatedValue = estimatedValue;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.isInsured = false;
    }

}
