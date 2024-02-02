package interfaces;

import entities.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;

/**
 *
 * @author danid
 */
public interface UserManager {

    /**
     * Retrieves a customer by their email address.
     *
     * @param user to log in
     * @return The user with the specified email address.
     * @throws ReadException If there is any Exception during processing.
     */
    public User signIn(User user) throws ReadException;

    /**
     * Retrieves a user by their email address.
     *
     * @param mail The email address of the user.
     * @return The user with the specified email address.
     * @throws ReadException If there is any Exception during processing.
     */
    public User findUserByMail(String mail) throws ReadException;

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @throws CreateException If there is any Exception during processing.
     */
    public void createUser(User user) throws CreateException;

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateUser(User user) throws UpdateException;

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteUser(String userId) throws DeleteException;
}
