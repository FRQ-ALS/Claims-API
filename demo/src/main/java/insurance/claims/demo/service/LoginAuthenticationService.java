package insurance.claims.demo.service;


import insurance.claims.demo.config.JwtUtil;
import insurance.claims.demo.dto.AppUser;
import insurance.claims.demo.dto.LoginRequest;
import insurance.claims.demo.dto.UserDTO;
import insurance.claims.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository accountRepository;


    //method that verifies user login and returns user details including JWT
    public UserDTO login(LoginRequest request) throws Exception {


        //using authentication manager to authenticate whether email or password are correct
        try {
            System.out.println("TEST");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()));

        } catch (BadCredentialsException e) {
            throw new Exception("Username or password incorrect");
        }

        AppUser appUser = accountRepository.findByEmail(request.getEmail()).get();

        System.out.println("JWT:" );
        String jwt = jwtUtil.generateToken(appUser);


        return new UserDTO(appUser.getUserID(),
                appUser.getFirstName(),
                appUser.getFirstName(),
                appUser.getAge(),
                appUser.isRegisteredCar(),
                jwt);
    }

}
