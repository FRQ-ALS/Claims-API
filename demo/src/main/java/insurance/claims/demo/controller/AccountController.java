package insurance.claims.demo.controller;


import insurance.claims.demo.service.AppUserService;
import insurance.claims.demo.dto.LoginRequest;
import insurance.claims.demo.dto.RegistrationRequest;
import insurance.claims.demo.service.LoginAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/account")
public class AccountController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private LoginAuthenticationService loginAuthenticationService;

    @PostMapping(path ="/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegistrationRequest request){

        return ResponseEntity.ok(appUserService.registerAccount(request));
    }

    @PostMapping(path="/authenticate")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {

        return ResponseEntity.ok(loginAuthenticationService.login(request));
    }

    @GetMapping(path = "/getProfile/{userID}")
    public ResponseEntity<?> getProfile(@PathVariable("userID") long userID) {

        return ResponseEntity.ok(appUserService.getUserProfile(userID));
    }
}
