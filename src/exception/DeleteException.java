package exception;

/**
 * Represents an exception that occurs when there is an error during the deletion of an object in the application's storage
 * @author Iñigo
 */
public class DeleteException extends Exception {

    /**
     * Creates a new instance of DeleteException without a detailed error message.
     */
    public DeleteException() {
    }

    /**
     * Constructs an instance of DeleteException with the specified detailed error message.
     * @param msg the detailed error message.
     */
    public DeleteException(String msg) {
        super(msg);
    }
}