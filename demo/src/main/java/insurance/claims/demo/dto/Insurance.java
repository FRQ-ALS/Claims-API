package insurance.claims.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Insurance {

    @SequenceGenerator(
            name = "insurance_sequence",
            sequenceName = "insurance_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "insurance_sequence"
    )
    private long insuranceID;
    private long userID;
    private long vehicleID;
    private long quoteID;
    private LocalDateTime insuranceExpiry;
    private String paymentType;
    private double paymentAmount;

    public Insurance(long userID, long vehicleID, long quoteID, LocalDateTime insuranceExpiry, String paymentType, double paymentAmount) {
        this.userID = userID;
        this.vehicleID = vehicleID;
        this.quoteID = quoteID;
        this.insuranceExpiry = insuranceExpiry;
        this.paymentType = paymentType;
        this.paymentAmount = paymentAmount;
    }
}
