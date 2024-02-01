/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import restful.CustomerRESTClient;
import restful.UserRESTClient;

/**
 *
 * @author danid
 */
public class UserManagerImplementation implements UserManager {

    protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

    private UserRESTClient client = new UserRESTClient();

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
