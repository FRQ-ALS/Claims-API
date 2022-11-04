package insurance.claims.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quote {
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
    private long quoteID;
    private long vehicleID;
    private long userID;
    private int driverAge;
    private LocalDateTime quoteExpiry;
    private double yearlyQuoteValue;
    private double monthlyQuoteValue;
    private boolean userAcceptance;
    private String regNumber;
    private String status;

    public Quote(long vehicleID, long userID, int driverAge,
                 LocalDateTime quoteExpiry, double yearlyQuoteValue,
                 double monthlyQuoteValue, boolean userAcceptance, String regNumber) {
        this.vehicleID = vehicleID;
        this.userID = userID;
        this.driverAge = driverAge;
        this.quoteExpiry = quoteExpiry;
        this.yearlyQuoteValue = yearlyQuoteValue;
        this.monthlyQuoteValue = monthlyQuoteValue;
        this.userAcceptance = userAcceptance;
        this.status = "active";
        this.regNumber = regNumber;
    }
}
