package ua.nure.brovenko.SummaryTask4.exception;

/**
 * ConnectionPool exception
 * @author Ivan Brovenko
 */
public class ConnectionPoolException extends Exception{

    /**
     * Exception constructor
     */
    public ConnectionPoolException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public ConnectionPoolException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
    
}
