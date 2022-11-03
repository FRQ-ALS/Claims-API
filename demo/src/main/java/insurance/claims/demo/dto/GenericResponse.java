package insurance.claims.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//This class acts as a generic response with a boolean identifier and message
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {

    private boolean actionPerformed;
    private String message;
}
