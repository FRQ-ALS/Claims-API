package insurance.claims.demo.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
        private String VIN;
        private long insuranceNo;
        private double claimAmount;
        private String make;
        private String model;
        private long year;

        public Claim(String VIN, long insuranceNo, double claimAmount, String make, String model, long year) {
                this.VIN = VIN;
                this.insuranceNo = insuranceNo;
                this.claimAmount = claimAmount;
                this.make = make;
                this.model = model;
                this.year = year;
        }

}
