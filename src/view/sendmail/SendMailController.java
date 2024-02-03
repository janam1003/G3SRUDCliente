package view.sendmail;

import entities.Customer;
import factories.CustomerManagerFactory;
import interfaces.CustomerManager;
import view.generic.GenericController;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Janam
 */
public class SendMailController extends GenericController {

    // TextField to write an email
    @FXML
    private TextField tfEmail;

    // Button to Send an email
    @FXML
    private Button btnSend;

    // Button to cancel remember password window
    @FXML
    private Button btnCancel;

    /**
     *
     * @param root
     */
    public void initStage(Parent root) {
        
        try {

            // Logger
            LOGGER.info("Initializing SendMail window.");

            // We create a new scene
            Scene scene = new Scene(root);

            //Creation of new stage to change its properties
            this.stage = new Stage();

            // We set the scene not resizable
            stage.setResizable(false);

            // We set the title Login
            stage.setTitle("Remember Password");

            // We set the window icon
            stage.getIcons().add(new Image("/resources/logo.png"));

            // We set the scene
            stage.setScene(scene);

            //Define the action when the window is closed
            stage.setOnCloseRequest(this::handleOnActionExit);

            //Define that the view is modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // We show the stage
            stage.show();

            // We set the default button
            btnSend.setDefaultButton(true);
            
            this.btnCancel.setCancelButton(true);

            // ToolTips
            btnSend.setTooltip(new Tooltip("Send"));
            
            btnCancel.setTooltip(new Tooltip("Cancel"));

            // Event Handlers
            this.btnSend.setOnAction(this::handleSendButtonAction);
            
            this.btnCancel.setOnAction(this::handleOnActionExit);
            
        } catch (Exception e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Unable to Initialize SendMail window.", e.getMessage());
            
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleSendButtonAction(ActionEvent event) {
        
        try {

            // Logger
            LOGGER.info("Initializing Send button action.");

            // If the mail or the password are empty, we throw an exception
            if (tfEmail.getText().trim().isEmpty()) {
                throw new Exception("Error, rellena todos los campos");
            }

            // If mail or/and password has more chars tham MAX_LENGTH we throw an exception
            if (tfEmail.getText().length() > this.MAX_LENGTH) {
                showErrorAlert("La longitud máxima del campo es de 255 caracteres");
            }

            // We check if the mail pattern is valid
            Pattern pattern = Pattern.compile(mailPattern);
            if (!pattern.matcher(tfEmail.getText()).matches()) {
                throw new Exception("Error, el mail no es valido");
            }
            
            CustomerManager customerManager = CustomerManagerFactory.getCustomerManager();
            Customer customer = null;
            customer = customerManager.findCustomerByMail(tfEmail.getText());
            if (customer == null) {
                throw new Exception("No exsite ninguna cuenta con ese mail");
            }
            customerManager.sendRecoveryMail(customer);
            customerManager.updateCustomer(customer, Boolean.TRUE);
            
        } catch (Exception e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Exception. {0}", e.getMessage());
            this.showErrorAlert(e.getMessage());
            
        }
    }
}
