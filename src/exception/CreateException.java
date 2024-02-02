package exception;

/**
 * Represents an exception that occurs when there is an error during the creation of an object in the application's storage
 * @author Iñigo
 */
public class CreateException extends Exception {

    /**
     * Creates a new instance of CreateException without a detailed error message.
     */
    public CreateException() {
    }
    /**
     * Constructs an instance of CreateException with the specified detailed error message.
     * @param msg the detailed error message.
     */
    public CreateException(String msg) {
        super(msg);
    }
}
