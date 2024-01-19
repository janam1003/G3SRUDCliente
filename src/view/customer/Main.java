package view.customer;

import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga el archivo FXML del controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerList.fxml"));

            
            // Crea la escena a partir del archivo FXML
            Parent root = loader.load();
            
            // Obtiene el controlador y llama a su método initStage
            CustomerListController controller = loader.getController();
            controller.initStage(root);
            
            // Configura y muestra la escena en el escenario
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}