/*
 * This class represents the implementation of the UserManager interface.
 * It provides methods for creating, updating, deleting, signing in, and finding users.
 * The class utilizes a UserRESTClient for communication with the REST service.
 * Logging is performed using a Logger instance.
 *
 * @author Dani
 */
package implementations;

import static encryption.EncryptionImplementation.encryptWithPublicKey;
import entities.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import interfaces.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import restful.UserRESTClient;

/**
 * Implementation of the UserManager interface.
 * Provides methods for managing user-related operations using a REST service.
 */
public class UserManagerImplementation implements UserManager {

    // Logger for logging information and errors
    protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

    // REST client for user-related operations
    private UserRESTClient client = new UserRESTClient();

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @throws CreateException If an error occurs during the creation process.
     */
    @Override
    public void createUser(User user) throws CreateException {
        try {
            LOGGER.info("Creating a new user");
            client.createUser_XML(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't create the customer", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        try {
            LOGGER.info("Updating a user");
            client.updateUser_XML(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't modify the customer", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a user based on the provided user ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws DeleteException If an error occurs during the deletion process.
     */
    @Override
    public void deleteUser(String userId) throws DeleteException {
        try {
            LOGGER.info("Deleting the customer");
            client.deleteUser(userId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't delete the customer", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Signs in a user, encrypting the password before sending it to the REST service.
     *
     * @param user The user attempting to sign in.
     * @return The signed-in user.
     * @throws ReadException If an error occurs during the sign-in process.
     */
    @Override
    public User signIn(User user) throws ReadException {
        try {
            user.setPassword(encryptWithPublicKey(user.getPassword()));
            user = client.signIn_XML(user, User.class);
            if (user == null) {
                return null;
            } else {
                return new User(user.getMail(), user.getPassword(), user.getCreationDate(), user.getUserType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Can't get the user", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Finds a user based on the provided email address.
     *
     * @param mail The email address of the user to be found.
     * @return The user with the specified email address.
     * @throws ReadException If an error occurs during the user retrieval process.
     */
    @Override
    public User findUserByMail(String mail) throws ReadException {
        User user = null;
        try {
            LOGGER.info("Finding all Users from REST service");
            user = client.getUserByMail_XML(User.class, mail);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find all Users", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }
}
