package fr.epf.restaurant.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class CommandeNotFoundException extends Exception{

    public CommandeNotFoundException(Throwable cause) {
        super(cause);
    }

    public CommandeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    

    
    

}
