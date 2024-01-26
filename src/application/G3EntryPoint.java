package application;

import static encryption.EncryptionImplementation.encryptWithPublicKey;
import entities.Customer;
import entities.Trip;
import java.util.List;
import javax.ws.rs.core.GenericType;
import restful.CustomerRESTClient;
import restful.TripRESTclient;

/**
 *
 * @author Janam
 */
public class G3EntryPoint {
    
    public static void main(String[] args){
        
        CustomerRESTClient client = new CustomerRESTClient();
        TripRESTclient client2 = new TripRESTclient();
        
    
        /*Customer customer = new Customer();
        customer.setName("Pepe");
        customer.setPassword("penelope");
        customer.setPassword(encryptWithPublicKey(customer.getPassword()));
        client.createCustomer_XML(customer);*/
        /*Trip trip = new Trip();
        trip.setDescription("sdvvvvvvvvvv");
        trip.setId(Integer.SIZE);
        client2.create(trip);*/
       
    
    }
    
    
}
