package exception;

/**
 * Represents an exception that occurs when there is an error during a read query in the application
 * @author Iñigo
 */
public class ReadException extends Exception {

    /**
     * Creates a new instance of ReadException without a detailed error message.
     */
    public ReadException() {
    }

    /**
     * Constructs an instance of ReadException with the specified detailed error message.
     * @param msg the detailed error message.
     */
    public ReadException(String msg) {
        super(msg);
    }
}
