package insurance.claims.demo.controller;

import insurance.claims.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(path = "/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        return imageService.uploadFile(file, request);
    }

    @PostMapping(path = "/download/{imageID}")
    public ResponseEntity<?> getImage(@PathVariable("imageID") long imageID) {

        return imageService.downloadFile(imageID);
    }
}
