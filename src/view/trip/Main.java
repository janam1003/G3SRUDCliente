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


            controller.initStage(root, customer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
