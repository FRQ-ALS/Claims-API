package insurance.claims.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {
    private long quoteID;
    private long userID;
    private String paymentType;
}
