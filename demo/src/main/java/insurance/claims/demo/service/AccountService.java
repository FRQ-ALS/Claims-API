package insurance.claims.demo.service;


import insurance.claims.demo.common.InvalidUserException;
import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;
    public GenericResponse registerAccount(RegistrationRequest request) {

        //Checking whether email is taken
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            return new GenericResponse(false,
                    "Email already exists!");
        }

        AppUser appUser = new AppUser(
                request.getEmail(),
                request.getPassword()
        );
        accountRepository.save(appUser);

        if (verifyAccountHasBeenCreated(request.getEmail())) {

            log.info("Account created with email: "+ appUser.getEmail());
            return new GenericResponse(true, "Your account has been created");
        }

        return new GenericResponse(false, "Unknown error occurred");
    }


    //method checks whether user exists in database
    private boolean verifyAccountHasBeenCreated(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    //method that verifies user login and returns user details
    public UserDTO login(LoginRequest request) {

        Optional<AppUser> user = accountRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) throw new InvalidUserException("User with that email is not found");

        if (!user.get().getPassword().equals(request.getPassword())) {
            throw new InvalidUserException("Incorrect email or password");
        }

        AppUser appUser = user.get();

        log.info("ID: " + appUser.getUserID());

        return new UserDTO(appUser.getUserID(),
                appUser.getFirstName(),
                appUser.getFirstName(),
                appUser.getAge(),
                appUser.isRegisteredCar());
    }
}
