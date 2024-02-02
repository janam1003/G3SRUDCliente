package view.customer;

import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
            // Carga el archivo FXML del controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerList.fxml"));

            
            // Crea la escena a partir del archivo FXML
            Parent root = (Parent)loader.load();
            
            // Obtiene el controlador y llama a su método initStage
            CustomerListController controller = loader.getController();
            
            controller.setStage(stage);
            controller.initStage(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}