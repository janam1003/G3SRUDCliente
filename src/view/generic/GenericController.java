package view.generic;


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This is the base class for all the controllers in the application.
 *
 * @author Iñigo
 */
public class GenericController {

    /**
     * Logger object used to log messages for application.
     */
    protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

    /**
     * Maximum text fields length.
     */
    protected final int MAX_LENGTH = 200;

    /**
     * Patterns for text fields validation.
     */
    protected String mailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    protected String namePattern = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$";
    protected String phonePattern = "^\\+?\\d{1,4}?\\d{7,10}$";
    protected String zipPattern = "^\\d{5}(-\\d{4})?$";
    protected String addressPattern = "^(?:[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+|\\d+)(?:\\s(?:[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+|\\d+))+$";
    protected String passwordPattern = "^(?=.*[A-Z])(?=.*[\\W_]).{8,}$";

    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the
     * controller to the Stage object in order to make its initialization. Note
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;

    /**
     * Gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.getDialogPane();
        alert.showAndWait();
    }

	/**
	 * Shows an infomation alert dialog.
	 * @param infoMsg The information message to be shown.
	 */
	protected void showInfoAlert(String infoMsg) {
		//Shows info dialog.
		Alert alert = new Alert(Alert.AlertType.INFORMATION, infoMsg, ButtonType.OK);
		alert.getDialogPane();
		alert.showAndWait();
	}
	
    /**
     * Handles the action when the user attempts to exit the application or
     * view.
     *
     * @param event The event triggered when the exit action is invoked.
     */
    public void handleOnActionExit(Event event) {

        try {

            // Logger
            LOGGER.info("Initializing handle on Exit action.");

            // We show an alert to confirm the exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Are you sure you want to exit?", ButtonType.OK,
                    ButtonType.CANCEL);

            // We wait for the user's response
            alert.showAndWait();

            // If the user clicks ok, we close the window
            if (alert.getResult() == ButtonType.OK) {

                this.stage.close();

                LOGGER.info("Window " + this.stage.getTitle() + " closed successfully.");

            } else {

                event.consume();
            }

        } catch (Exception e) {

            String errorMsg = "Error exiting application:" + e.getMessage();

            this.showErrorAlert(errorMsg);

            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }
}
