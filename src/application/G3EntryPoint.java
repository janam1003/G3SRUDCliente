package application;

import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.signin.LoginController;

/**
 * Starting point of an application
 *
 * @author Janam
 */
public class G3EntryPoint extends Application {

    /**
     * Logger object used to log messages for the application.
     */
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    /**
     * Open the login window
     *
     * @param stage Stage where the scene will be projected
     * @throws Exception all exceptions
     */
    @Override
    public void start(Stage stage) throws Exception {

        LOGGER.info("Initializing start method to open signin window.");

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signin/Login.fxml"));

        // Get the root
        Parent root = (Parent) loader.load();

        // Get the controller
        LoginController controller = (LoginController) loader.getController();

        // Set the stage
        controller.setStage(stage);

        // Initialize the stage
        controller.initStage(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
