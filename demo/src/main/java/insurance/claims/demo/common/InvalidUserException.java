package insurance.claims.demo.common;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidUserException extends RuntimeException{

    public InvalidUserException(String messageInfo) {
        super(messageInfo);
    }
}
