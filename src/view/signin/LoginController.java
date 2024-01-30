package view.signin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.regex.Pattern;
import view.generic.GenericController;
import view.signup.SignUpController;
import java.io.IOException;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import view.sendmail.SendMailController;

/**
 * FXML Controller class
 *
 * @author Iñigo
 */
public class LoginController extends GenericController {

    /**
     * Text field for the user's mail.
     */
    @FXML
    private TextField tfMail;

    /**
     * Button to log in.
     */
    @FXML
    private Button btnLogIn;

    /**
     * Button to Eye.
     */
    @FXML
    private Button btnEye;

    /**
     * Hyperlink to sign up.
     */
    @FXML
    private Hyperlink hlSignUp;

    /**
     * Hyperlink to sign up.
     */
    @FXML
    private Hyperlink hlResetPassword;

    /**
     * Text field for the user's password.
     */
    @FXML
    private PasswordField pfPassword;

    /**
     * Icon for the button to show the password.
     */
    @FXML
    private FontAwesomeIcon btEye;

    /**
     * Text field to show the password.
     */
    @FXML
    private TextField tfPasswordReveal;

    /**
     * Method to initialize the stage.
     *
     * @param root FXML document graph.
     */
    public void initStage(Parent root) {

        try {

            // Logger
            LOGGER.info("Initializing Login stage.");

            // We create a new scene
            Scene scene = new Scene(root);

            // We set the scene not resizable
            stage.setResizable(false);

            // We set the title Login
            stage.setTitle("Login");

            // We set the window icon
            stage.getIcons().add(new Image("/Resources/logo.png"));

            // We set the scene
            stage.setScene(scene);

            // We set the default button
            btnLogIn.setDefaultButton(true);

            // We set the cancel button
            tfPasswordReveal.setVisible(false);

            // Define the action when the x for exit on the window is clicked
            stage.setOnCloseRequest(this::handleOnActionExit);

            // Event Handlers
            this.hlSignUp.setOnAction(this::handleSignUpHyperlinkAction);

            // Event Handlers
            this.hlResetPassword.setOnAction(this::handleResetPasswordHyperlinkAction);

            this.btnLogIn.setOnAction(this::handleLogInButtonAction);

            this.btnEye.setOnAction(this::handleEyeButtonAction);

            // We show the stage
            stage.show();

        } catch (Exception e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Unable to Initialize Login window: {0}", e.getMessage());

            this.showErrorAlert(e.getMessage());

        }
    }

    /**
     * Method to handle the LogIn button action.
     *
     * @param event An action event.
     */
    @FXML
    private void handleLogInButtonAction(ActionEvent event) {

        try {

            // Logger
            LOGGER.info("Initializing login button action.");

            // If the password is revealed, we get it text to set it into password field
            if (tfPasswordReveal.isVisible()) {
                pfPassword.setText(tfPasswordReveal.getText());
            }

            // If the mail or the password are empty, we throw an exception
            if (tfMail.getText().trim().isEmpty() || pfPassword.getText().isEmpty()) {
                throw new Exception("Error, rellena todos los campos");
            }

            // If mail or/and password has more chars tham MAX_LENGTH we throw an exception
            if (tfMail.getText().length() > this.MAX_LENGTH || pfPassword.getText().length() > this.MAX_LENGTH) {
                showErrorAlert("La longitud máxima del campo es de 255 caracteres");
            }

            // We check if the mail pattern is valid
            Pattern pattern = Pattern.compile(mailPattern);
            if (!pattern.matcher(tfMail.getText()).matches()) {
                throw new Exception("Error, el mail no es valido");
            }

        } catch (Exception e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Exception. {0}", e.getMessage());
            this.showErrorAlert(e.getMessage());

        }
    }

    /**
     * Method to handle the SignUp hyperlink action.
     *
     * @param event An action event.
     */
    @FXML
    private void handleSignUpHyperlinkAction(ActionEvent event) {

        try {

            // Logger
            LOGGER.info("Initializing SignUp Hypwrlink Action.");

            // We load the SignUp view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signup/SignUp.fxml"));

            // We get the root
            Parent root = (Parent) loader.load();

            // We get the controller
            SignUpController controller = (SignUpController) loader.getController();

            // We set the stage
            controller.setStage(stage);

            // We init the stage
            controller.initStage(root);

        } catch (IOException e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Exception: {0}", e.getMessage());
            this.showErrorAlert(e.getMessage());

        }
    }

    /**
     * Method to handle the Reset Password hyperlink action.
     *
     * @param event An action event.
     */
    @FXML
    private void handleResetPasswordHyperlinkAction(ActionEvent event) {

        try {

            // Logger
            LOGGER.info("Initializing Reset Password Hypwrlink Action.");

            // We load the SignUp view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendmail/SendMail.fxml"));

            // We get the root
            Parent root = (Parent) loader.load();

            // We get the controller
            SendMailController controller = (SendMailController) loader.getController();

            // We set the stage
            controller.setStage(stage);

            // We init the stage
            controller.initStage(root);

        } catch (IOException e) {

            // Logger
            LOGGER.log(Level.SEVERE, "Exception: {0}", e.getMessage());
            this.showErrorAlert(e.getMessage());

        }
    }

    /**
     * Method to handle the Eye button action.
     *
     * @param event An action event.
     */
    @FXML
    private void handleEyeButtonAction(ActionEvent event) {

        // Logger
        LOGGER.info("Initializing handle on eye button action.");

        // We call the showPassword method
        showPassword(btEye, pfPassword, tfPasswordReveal);
    }
}
