package ua.nure.brovenko.SummaryTask4.exception;

/**
 * AuthenticationTechnical exception
 * @author Ivan Brovenko
 */
public class AuthenticationTechnicalException extends Exception {

    /**
     * Exception constructor
     */
    public AuthenticationTechnicalException(){
    }

    /**
     * Exception constructor
     * @param message message
     */
    public AuthenticationTechnicalException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public AuthenticationTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public AuthenticationTechnicalException(Throwable cause) {
        super(cause);
    }
    
}
