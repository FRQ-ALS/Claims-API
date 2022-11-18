package insurance.claims.demo.service;

import insurance.claims.demo.config.JwtDecompiler;
import insurance.claims.demo.dto.AppUser;
import insurance.claims.demo.dto.Image;
import insurance.claims.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private JwtDecompiler jwtDecompiler;


    public ResponseEntity<?> uploadFile(MultipartFile file, HttpServletRequest request) {
        AppUser user = jwtDecompiler.getUserFromJwtToken(request);


        //crating new image and saving to repository
        try {
            Image image = Image.builder()
                    .image(file.getBytes())
                    .userID(user.getUserID())
                    .fileType(file.getContentType()).build();

            imageRepository.save(image);

        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Image was not uploaded");
        }


        return ResponseEntity.ok("Image uploaded");
    }

    //method that returns image
    public ResponseEntity<?> downloadFile(long imageID) {
        Optional<Image> image = imageRepository.findById(imageID);

        if (image.isEmpty()) return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("Image not found");

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.get().getFileType()))
                .body(new ByteArrayResource(image.get().getImage()));
    }
}
