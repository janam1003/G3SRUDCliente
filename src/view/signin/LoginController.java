package view.signin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Customer;
import static entities.EnumUserType.ADMIN;
import static entities.EnumUserType.CUSTOMER;
import entities.User;
import factories.UserManagerFactory;
import interfaces.UserManager;
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
import view.customer.CustomerListController;
import view.sendmail.SendMailController;
import view.trip.TripController;

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
	 * Loader for the trip window.
	 */
	private FXMLLoader loaderTrip;
	/**
	 * Loader for the admin window.
	 */
	private FXMLLoader loaderAdmin;

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
			stage.getIcons().add(new Image("/resources/logo.png"));

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

			// Load the FXML file for the trip window
			loaderTrip = new FXMLLoader(getClass().getResource("/view/trip/Trip.fxml"));
			// Load the FXML file for the Admin window
			loaderAdmin = new FXMLLoader(getClass().getResource("/view/customer/CustomerList.fxml"));

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

			// THIS IS A BACK DOOR FOR THE CUSTOMER WINDOW
			if ("admin@gmail.com".equals(tfMail.getText()) && "abcd*1234".equals(pfPassword.getText())) {

				LOGGER.info("Initializing start method to open customer window.");

				// Get the root
				Parent root = (Parent) loaderAdmin.load();

				// Get the controller
				CustomerListController controller = (CustomerListController) loaderAdmin.getController();

				// Set the stage
				controller.setStage(stage);

				// Initialize the stage
				controller.initStage(root);

				// Close the login window
				//stage.close();

				return;
			}

			// THIS IS A BACK DOOR FOR THE TRIP WINDOW I.E A USER IS CUSTOMER
			if ("customer@gmail.com".equals(tfMail.getText()) && "abcd*1234".equals(pfPassword.getText())) {

				LOGGER.info("Initializing start method to open trip window.");

				// Get the root
				Parent root = (Parent) loaderTrip.load();

				// Get the controller
				TripController controller = (TripController) loaderTrip.getController();

				// Set the stage
				controller.setStage(stage);

				// create the object of the logged customer
				Customer customer = new Customer();

				customer.setMail("customer@gmail.com");

				// Initialize the stage
				controller.initStage(root, customer);

				// Close the login window
				stage.close();

				return;
			}

			User user = new User();

			user.setMail(tfMail.getText());

			user.setPassword(pfPassword.getText());

			UserManager userManager = UserManagerFactory.getUserManager();

			user = userManager.signIn(user);

			if (user == null) {

				// Handle the case when the user is not logged in.
				showErrorAlert("Login failed. Please check your credentials.");

				// Handle the case when the user is an admin.
			} else if (user.getUserType() == ADMIN) {

				LOGGER.info("Initializing start method to open signin window.");

				// Get the root
				Parent root = (Parent) loaderAdmin.load();

				// Get the controller
				CustomerListController controller = (CustomerListController) loaderAdmin.getController();

				// Set the stage
				controller.setStage(stage);

				// Initialize the stage
				controller.initStage(root);

				// Close the login window
				//stage.close();

				// Handle the case when the user is a customer.
			} else if (user.getUserType() == CUSTOMER) {

				LOGGER.info("Initializing start method to open signin window.");

				// Get the root
				Parent root = (Parent) loaderTrip.load();

				// Get the controller
				TripController controller = (TripController) loaderTrip.getController();

				// Set the stage
				controller.setStage(stage);

				// create the object of the logged customer
				Customer customer = new Customer();

				customer.setMail(user.getMail());

				// Initialize the stage
				controller.initStage(root, customer);

				// Close the login window
				stage.close();

			} else {

				// Handle the case when the user type is neither admin nor customer.
				showErrorAlert("An unexpected error occurred. Please try again later.");

			}

		} catch (Exception e) {

			// Logger
			LOGGER.log(Level.SEVERE, "Exception. {0}", e.getMessage());

			showErrorAlert("Unexpected error: " + e.getMessage());
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
