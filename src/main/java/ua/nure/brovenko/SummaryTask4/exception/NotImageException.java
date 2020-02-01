package ua.nure.brovenko.SummaryTask4.exception;

/**
 * Created by admin on 23.01.2018.
 */
public class NotImageException extends Exception {

    /**
     * Exception constructor
     */
    public NotImageException() {
    }

    /**
     * Exception constructor
     * @param message message
     */
    public NotImageException(String message) {
        super(message);
    }

    /**
     * Exception constructor
     * @param message message
     * @param cause cause
     */
    public NotImageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception constructor
     * @param cause cause
     */
    public NotImageException(Throwable cause) {
        super(cause);
    }
}
