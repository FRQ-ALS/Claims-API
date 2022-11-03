package insurance.claims.demo.service;


import insurance.claims.demo.common.InvalidUserException;
import insurance.claims.demo.dto.*;
import insurance.claims.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public GenericResponse registerAccount(RegistrationRequest request) {

        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            return new GenericResponse(false,
                    "Email already exists!");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword()
        );
        accountRepository.save(user);

        if (verifyAccountHasBeenCreated(user)) {
            return new GenericResponse(true, "Your account has been created");
        }

        return new GenericResponse(false, "Unknown error occurred");
    }


    //method checks whether user exists in database
    private boolean verifyAccountHasBeenCreated(User user) {
        return accountRepository.findByEmail(user.getEmail()) != null;
    }



    //method that verifies user login and returns user details
    public UserDTO login(LoginRequest request) {

        Optional<User> user = accountRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) throw new InvalidUserException("User with that email is not found");

        if (!user.get().getPassword().equals(request.getPassword())) {
            throw new InvalidUserException("Incorrect email or password");
        }

        return new UserDTO(user.get().getUserID(),
                user.get().getFirstName(), user.get().getLastName(), user.get().getAge());

    }
}
