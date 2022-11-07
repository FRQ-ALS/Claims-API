package insurance.claims.demo.dto;

import antlr.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDTO {
    private String insuranceID;
    private String regNumber;
    private LocalDateTime insuranceExpiry;
}
