package ua.nure.brovenko.SummaryTask4.exception;

/**
 * DAOLogical exception
 * @author Ivan Brovenko
 */
public class DAOLogicalException extends Exception{

    /**
     * Exception constructor
     */
    public DAOLogicalException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public DAOLogicalException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public DAOLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public DAOLogicalException(Throwable cause) {
        super(cause);
    }
    
}
