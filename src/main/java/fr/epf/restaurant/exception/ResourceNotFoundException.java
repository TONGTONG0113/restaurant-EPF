package fr.epf.restaurant.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String message) {
        super(message);
    }

    
    public ResourceNotFoundException(String resourceName, Long id) {
    
        super(resourceName + " introuvable avec l'id : " + id);

    }
    
    


}
