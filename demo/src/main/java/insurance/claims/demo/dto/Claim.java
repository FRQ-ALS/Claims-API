package insurance.claims.demo.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Claim {
        @SequenceGenerator(
                name = "claim_sequence",
                sequenceName = "claim_sequence",
                allocationSize = 1
        )
        @Id
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "claim_sequence"
        )
        private long claimId;

        private long userID;
        private long insuranceNo;
        private double claimAmount;
        private LocalDateTime claimExpiry;

        private boolean expired;

        public Claim(long insuranceNo, double claimAmount, LocalDateTime claimExpiry, long userID) {
                this.insuranceNo = insuranceNo;
                this.claimAmount = claimAmount;
                this.claimExpiry = claimExpiry;
                this.userID = userID;
                this.expired = false;
        }
}
