package ua.nure.brovenko.SummaryTask4.exception;

/**
 * DAOTechnical exception
 * @author Ivan Brovenko
 */
public class DAOTechnicalException extends Exception{

    /**
     * Exception constructor
     */
    public DAOTechnicalException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public DAOTechnicalException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public DAOTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public DAOTechnicalException(Throwable cause) {
        super(cause);
    }
    
}
