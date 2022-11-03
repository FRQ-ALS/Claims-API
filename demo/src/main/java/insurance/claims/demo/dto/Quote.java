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


}
