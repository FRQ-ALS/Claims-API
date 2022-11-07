package insurance.claims.demo.service;


import insurance.claims.demo.common.InvalidUserException;
import insurance.claims.demo.config.JwtUtil;
import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public GenericResponse registerAccount(RegistrationRequest request) {

        //Checking whether email is taken
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            return new GenericResponse(false,
                    "Email already taken!");
        }

        //encoding password using bCrypt

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        //creating new User and saving  it

        AppUser appUser = new AppUser(
                request.getEmail(),
                encodedPassword
        );

        accountRepository.save(appUser);

        if (verifyAccountHasBeenCreated(request.getEmail())) {

            return new GenericResponse(true, "Your account has been created");
        }


        return new GenericResponse(false, "Unknown error occurred");
    }


    //method checks whether user exists in database
    private boolean verifyAccountHasBeenCreated(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }


    //implementing UserDetailsService mandatory method

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) accountRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("User with email %s not found", email)));
    }
}
