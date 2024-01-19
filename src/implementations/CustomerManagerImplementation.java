/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

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
 *
 * @author danid
 */
public class CustomerManagerImplementation implements CustomerManager {
    /**
	 * Logger object used to log messages for application.
	 */
	protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

    private CustomerRESTClient client = new CustomerRESTClient();

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

    @Override
    public Customer findCustomerByMail(String mail) throws ReadException {
        Customer customer = null;
        try {
            LOGGER.info("Finding all Customers from REST service");
            customer = client.getCustomerByMail_XML(Customer.class, mail);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find all Users", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findCustomersWithTrips() throws ReadException {
        List<Customer> customers = null;
        try {
            LOGGER.info("Finding all Customers with trips from REST service");
            customers = client.getCustomersWithTrips_XML(new GenericType<List<Customer>>() {
            });

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't find all Users with trips", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return customers;

    }

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

    @Override
    public void createCustomer(Customer customer) throws CreateException {

        try {
            LOGGER.info("Creating a new customer");
            client.createCustomer_XML(customer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't create the customer", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws UpdateException {
        try {
            LOGGER.info("Updating a customer");
            client.updateCustomer_XML(customer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't modify the customer", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

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
