/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementations.CustomerManagerImplementation;
import interfaces.CustomerManager;
import java.util.logging.Logger;

/**
 * Factory class responsible for creating instances of the CustomerManager
 * interface. This class provides a method to obtain an implementation of the
 * CustomerManager.
 *
 * @author danid
 */
public class CustomerManagerFactory {

    /**
     * Logger object used to log messages for the application.
     */
    private static final Logger LOGGER = Logger.getLogger(CustomerManagerFactory.class.getName());

    /**
     * Retrieves an instance of the CustomerManager interface.
     *
     * @return An implementation of the CustomerManager.
     */
    public static CustomerManager getCustomerManager() {
        LOGGER.info("Initializing Customer Manager.");

        return new CustomerManagerImplementation();
    }

}
