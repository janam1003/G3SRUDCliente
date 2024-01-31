package application;

import encryption.EncryptionImplementation;
import entities.Customer;
import entities.EnumTripType;
import entities.EnumUserType;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import entities.User;
import exception.CreateException;
import exception.ReadException;
import factories.CustomerManagerFactory;
import factories.UserManagerFactory;
import interfaces.CustomerManager;
import interfaces.UserManager;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import restful.CustomerRESTClient;
import restful.TripInfoRESTClient;
import restful.TripRESTclient;
import restful.UserRESTClient;

public class G3EntryPoint {

    public static void main(String[] args) {
        // Create instances of your REST clients
        CustomerRESTClient customerClient = new CustomerRESTClient();
        CustomerManager mang = CustomerManagerFactory.getCustomerManager();
        UserManager manU = UserManagerFactory.getUserManager();
        User user = new User();
        User user2 = new User();


        try {
            Customer cust= new Customer();
            cust.setMail("porfaporfa1@mail.com");
            cust.setUserType(EnumUserType.CUSTOMER);
            cust.setPassword("uwu");
            cust.setCreationDate(null);
            try {
                mang.createCustomer(cust);
            } catch (CreateException ex) {
                Logger.getLogger(G3EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            user2.setMail("porfaporfa1@mail.com");
            user2.setPassword("uwu");
            
            
            user=manU.signIn(user2);
            System.out.println("PORFAA USER DEVUELTO: " + user);
        } catch (ReadException ex) {
            Logger.getLogger(G3EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    }
