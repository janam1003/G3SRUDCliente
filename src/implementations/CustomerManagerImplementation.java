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
import javax.ws.rs.WebApplicationException;
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

    private final CustomerRESTClient client = new CustomerRESTClient();

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
            customer.setPassword(encryptWithPublicKey(customer.getPassword()));
            client.createCustomer_XML(customer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't create the customer", e.getMessage());
            //throw new CreateException(e.getMessage());
            e.printStackTrace();
        }
    }

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

    /**
     * Sends a recovery email to the customer.
     *
     * @param customer The customer to whom the recovery email should be sent.
     * @throws ReadException If there is any issue during the process.
     */
    @Override
    public void sendRecoveryMail(Customer customer) throws ReadException {
        try {
            LOGGER.info("Sending an email to the customer for recovery.");
            client.sendRecoveryEmail(customer);
            LOGGER.info("Recovery email sent successfully.");
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error sending a recovery email to the customer.", e.getMessage());
            throw new ReadException("Error sending recovery email: " + e.getMessage());
        }
    }
}
