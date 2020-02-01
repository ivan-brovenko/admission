package ua.nure.brovenko.SummaryTask4.exception;

/**
 * AuthenticationLogical exception
 * @author Ivan Brovenko
 */
public class AuthenticationLogicalException extends Exception{

    /**
     * Exception constructor
     */
    public AuthenticationLogicalException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public AuthenticationLogicalException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public AuthenticationLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public AuthenticationLogicalException(Throwable cause) {
        super(cause);
    }
    
}
