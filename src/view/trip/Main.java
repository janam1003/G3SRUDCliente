package view.trip;

import entities.Customer;
import entities.EnumTripType;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import java.util.Date;
import java.util.List;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.ws.rs.core.GenericType;
import restful.CustomerRESTClient;
import restful.TripInfoRESTClient;
import restful.TripRESTclient;
import restful.UserRESTClient;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga el archivo FXML del controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Trip.fxml"));

            // Crea la escena a partir del archivo FXML
            Parent root = (Parent) loader.load();

            // Obtiene el controlador y llama a su método initStage
            TripController controller = loader.getController();
            controller.setStage(primaryStage);
            Customer customer = new Customer();

            customer.setMail("test@gmail.com");
            customer.setPassword("pass");
            
            TripRESTclient tripClient = new TripRESTclient();
            TripInfoRESTClient tripInfoClient = new TripInfoRESTClient();
            CustomerRESTClient customerClient = new CustomerRESTClient();
            UserRESTClient userClient = new UserRESTClient();

            // Create a new Trip
//            Trip trip = new Trip();
//            trip.setId(8);
//            trip.setDescription("Test cambiadoo");
//            trip.setTripType(EnumTripType.CULTURE);
//            tripClient.update(trip);

            customer.setName("Test cambiadoo");
            customer.setZip(12345);
            customer.setAddress("Test address");
            customer.setPhone(1234567890);
            customerClient.createCustomer_XML(customer);
//
//            // Create a new TripInfo
//            TripInfo tripInfo = new TripInfo();
//            TripInfoId tripInfoId = new TripInfoId(8, "test4@mail.com");
//            tripInfo.setTripInfoId(tripInfoId);
//            trip.setId(8);
//            Date date1 = new Date(2024, 10, 10);
//            tripInfo.setInitialDate(date1);
//            tripInfo.setTrip(trip);
//            tripInfo.setCustomer(customer);
//            tripInfoClient.update(tripInfo);
            //List<Trip> list = tripClient.findAllTrips(new GenericType<List<Trip>>(){});
            // Close the clients
            tripClient.close();
            tripInfoClient.close();
            customerClient.close();
            userClient.close();
            controller.initStage(root, customer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
