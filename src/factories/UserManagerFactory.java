/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

/**
 *
 * @author danid
 */
import implementations.UserManagerImplementation;
import interfaces.UserManager;
import java.util.logging.Logger;

public class UserManagerFactory {
    /**
	 * Logger object used to log messages for the application.
	 */
	private static final Logger LOGGER = Logger.getLogger(UserManagerFactory.class.getName());
    
    /**
     * Returns an instance of UserManager that is implemented by
     * UserManagerImplementation.
     * 
     * @return an instance of UserManager
     */
    public static UserManager getUserManager(){
        LOGGER.info("Getting Customer Manager.");
        return new UserManagerImplementation();
    }
    
}