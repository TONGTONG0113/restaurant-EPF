package fr.epf.restaurant.exception;

public class StockInsuffissantException extends Exception{

    public StockInsuffissantException(Throwable cause) {
        super(cause);
    }

    public StockInsuffissantException(String message, Throwable cause) {
        super(message, cause);
    }
    

}
