package insurance.claims.demo.dto;


import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userID;

    private String fileType;
    //specifies that the database could store the file as a large object

    @Lob
    private byte[] image;


}
