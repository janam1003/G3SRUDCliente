package application;

import entities.Customer;
import entities.EnumTripType;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import java.util.Date;
import restful.CustomerRESTClient;
import restful.TripInfoRESTClient;
import restful.TripRESTclient;
import restful.UserRESTClient;

public class G3EntryPoint {

    public static void main(String[] args) {
        // Create instances of your REST clients
        TripRESTclient tripClient = new TripRESTclient();
        TripInfoRESTClient tripInfoClient = new TripInfoRESTClient();
        CustomerRESTClient customerClient = new CustomerRESTClient();
        UserRESTClient userClient = new UserRESTClient();

        try {
           // Create a new Trip
        Trip trip = new Trip();
        trip.setId(8);
        trip.setDescription("Test cambiadoo");
        trip.setTripType(EnumTripType.CULTURE);
        tripClient.update(trip);

        // Create a new Customer
        Customer customer = new Customer();
        customer.setMail("test4@mail.com");
        customer.setPassword("testPAssword");
        customer.setName("Test cambiadoo");
        customer.setZip(12345);
        customer.setAddress("Test address");
        customer.setPhone(1234567890);
        customerClient.updateCustomer_XML(customer);

        // Create a new TripInfo
        TripInfo tripInfo = new TripInfo();
        TripInfoId tripInfoId = new TripInfoId(8, "test4@mail.com");
        tripInfo.setTripInfoId(tripInfoId);
        trip.setId(8);
        Date date1 = new Date(2024,10,10);
        tripInfo.setInitialDate(date1);
        tripInfo.setTrip(trip);
        tripInfo.setCustomer(customer);
        tripInfoClient.update(tripInfo);
            

        // Close the clients
        tripClient.close();
        tripInfoClient.close();
        customerClient.close();
        userClient.close();
        } finally {
            // Close the clients to release resources
            tripClient.close();
            tripInfoClient.close();
            customerClient.close();
            userClient.close();
        }
    }
}
