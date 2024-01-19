/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Customer;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;

/**
 * Local interface for managing Customer entities.
 */
public interface CustomerManager {

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<Customer> findAllCustomers() throws ReadException;

    /**
     * Retrieves a customer by their email address.
     *
     * @param mail The email address of the customer.
     * @return The customer with the specified email address.
     * @throws ReadException If there is any Exception during processing.
     */
    public Customer findCustomerByMail(String mail) throws ReadException;

    /**
     * Retrieves all customers with associated trips.
     *
     * @return A list of customers with associated trips.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<Customer> findCustomersWithTrips() throws ReadException;
    /**
     * Retrieves all customers ordered by they day they where created.
     *
     * @return A list of customers with customers ordered by they day they where created.
     * @throws ReadException If there is any Exception during processing.
     */
    
    public List<Customer> findAllOrderByCreationDate() throws ReadException;
    /**
     * Retrieves all customers with trips longer than one week.
     *
     * @return A list of customers trips longer than one week.
     * @throws ReadException If there is any Exception during processing.
     */
    
    
    public List<Customer> findOneWeekTrips() throws ReadException;
        /**
     * Creates a new customer.
     *
     * @param customer The customer to be created.
     * @throws CreateException If there is any Exception during processing.
     */
    public void createCustomer(Customer customer) throws CreateException;

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to be updated.
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateCustomer(Customer customer) throws UpdateException;

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId The ID of the customer to be deleted.
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteCustomer(String customerId) throws DeleteException;
}
