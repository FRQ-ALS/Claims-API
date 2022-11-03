package insurance.claims.demo.controller;


import insurance.claims.demo.service.AccountService;
import insurance.claims.demo.dto.LoginRequest;
import insurance.claims.demo.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path ="/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegistrationRequest request){

        return ResponseEntity.ok(accountService.registerAccount(request));
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(accountService.login(request));
    }
}
