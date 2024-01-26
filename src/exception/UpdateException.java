package exception;

/**
 * Represents an exception that occurs when there is an error updating an object in the application
 * @author Iñigo
 */
public class UpdateException extends Exception {

    /**
     * Creates a new instance of UpdateException without a detailed error message.
     */
    public UpdateException() {
    }

    /**
     * Constructs an instance of UpdateException with the specified detailed error message.
     * @param msg the detailed error message.
     */
    public UpdateException(String msg) {
        super(msg);
    }
}
