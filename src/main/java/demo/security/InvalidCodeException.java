package demo.security;

/**
 * Created by kuntze on 23/04/15.
 */
public class InvalidCodeException extends Exception {

    public InvalidCodeException(String message) {
        super(message);
    }
}
