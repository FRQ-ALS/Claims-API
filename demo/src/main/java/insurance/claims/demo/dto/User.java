package insurance.claims.demo.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private long userID;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    private int age;

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
