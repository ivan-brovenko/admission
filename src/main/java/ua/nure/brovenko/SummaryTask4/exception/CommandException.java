package ua.nure.brovenko.SummaryTask4.exception;

/**
 * Command exception
 * @author Ivan Brovenko
 */
public class CommandException extends Exception{

    /**
     * Exception constructor
     */
    public CommandException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
    
}
