/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import static encryption.EncryptionImplementation.encryptWithPublicKey;
import entities.Customer;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import interfaces.CustomerManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import restful.CustomerRESTClient;

/**
 * Implementation of the CustomerManager interface that communicates with a RESTful service.
 * This class provides methods to interact with customer data such as finding all customers,
 * finding a customer by email, and finding customers with trips.
 *
 * @author danid
 */
public class CustomerManagerImplementation implements CustomerManager {

    /**
     * Logger object used to log messages for the application.
     */
    protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

    private CustomerRESTClient client = new CustomerRESTClient();

    /**
     * Retrieves a list of all customers from the REST service.
     *
     * @return A list of Customer objects.
     * @throws ReadException If an error occurs while retrieving the data.
     */
    @Override
    public List<Customer> findAllCustomers() throws ReadException {
        List<Customer> customers = null;
        try {
            LOGGER.info("Finding all Customers from REST service");
            customers = client.getAllCustomers_XML(new GenericType<List<Customer>>() {
            });

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find all Users", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customers;
    }

    /**
     * Retrieves a customer by their email address from the REST service.
     *
     * @param mail The email address of the customer.
     * @return The Customer object with the specified email address.
     * @throws ReadException If an error occurs while retrieving the data.
     */
    @Override
    public Customer findCustomerByMail(String mail) throws ReadException {
        Customer customer = null;
        try {
            LOGGER.info("Finding Customer by email from REST service");
            customer = client.getCustomerByMail_XML(Customer.class, mail);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find User by email", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customer;
    }

    /**
     * Retrieves a list of customers with associated trips from the REST service.
     *
     * @return A list of Customer objects with associated trips.
     * @throws ReadException If an error occurs while retrieving the data.
     */
    @Override
    public List<Customer> findCustomersWithTrips() throws ReadException {
        List<Customer> customers = null;
        try {
            LOGGER.info("Finding Customers with trips from REST service");
            customers = client.getCustomersWithTrips_XML(new GenericType<List<Customer>>() {
            });

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find Users with trips", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customers;
    }

    /**
 * Retrieves a list of all customers ordered by creation date from the REST service.
 *
 * @return List of Customer objects ordered by creation date.
 * @throws ReadException If there is an error while reading data from the REST service.
 */
@Override
public List<Customer> findAllOrderByCreationDate() throws ReadException {

    List<Customer> customers = null;
    try {
        LOGGER.info("Finding all Customers in order from REST service");
        customers = client.getCustomersOrderByCreationDate_XML(new GenericType<List<Customer>>() {
        });

    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Can't find all Users in order", e.getMessage());
        throw new ReadException(e.getMessage());
    }
    return customers;
}

/**
 * Retrieves a list of customers with one-week trips from the REST service.
 *
 * @return List of Customer objects with one-week trips.
 * @throws ReadException If there is an error while reading data from the REST service.
 */
@Override
public List<Customer> findOneWeekTrips() throws ReadException {
    List<Customer> customers = null;
    try {
        LOGGER.info("Finding all Customers with one week trips from REST service");
        customers = client.getCustomersOneWeekTrips_XML(new GenericType<List<Customer>>() {
        });

    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Can't find all Users with one week trips", e.getMessage());
        throw new ReadException(e.getMessage());
    }
    return customers;
}

/**
 * Creates a new customer using the provided customer object.
 *
 * @param customer The Customer object to be created.
 * @throws CreateException If there is an error while creating the customer.
 */
@Override
public void createCustomer(Customer customer) throws CreateException {

    try {
        LOGGER.info("Creating a new customer");
        customer.setPassword(encryptWithPublicKey(customer.getPassword()));
        client.createCustomer_XML(customer);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Can't create the customer", e.getMessage());
        //throw new CreateException(e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Updates an existing customer using the provided customer object.
 *
 * @param customer  The Customer object to be updated.
 * @param encrypted A boolean indicating whether the password is already encrypted.
 * @throws UpdateException If there is an error while updating the customer.
 */
@Override
public void updateCustomer(Customer customer, Boolean encrypted) throws UpdateException {
    try {
        LOGGER.info("Updating a customer");
        if (!encrypted) {
            customer.setPassword(encryptWithPublicKey(customer.getPassword()));
        }
        client.updateCustomer_XML(customer);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Can't modify the customer", e.getMessage());
        throw new UpdateException(e.getMessage());
    }
}

/**
 * Deletes a customer with the specified ID from the REST service.
 *
 * @param customerId The ID of the customer to be deleted.
 * @throws DeleteException If there is an error while deleting the customer.
 */
@Override
public void deleteCustomer(String customerId) throws DeleteException {
    try {
        LOGGER.info("Deleting the customer");
        client.deleteCustomer(customerId);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Can't delete the customer", e.getMessage());
        throw new DeleteException(e.getMessage());
    }
}
}