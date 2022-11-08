package insurance.claims.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String fullName;
    private int age;
    private String regNumber;
    private String carMake;
    private int carYear;
}
