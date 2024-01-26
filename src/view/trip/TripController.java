package view.trip;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import view.generic.GenericController;
import entities.Trip;
import entities.TripInfo;
import factories.TripManagerFactory;
import interfaces.TripManager;

import java.io.IOException;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @autor Iñigo
 */
public class TripController extends GenericController {
	/**
	 * Logger object used to log messages for application.
	 */
	protected static final Logger LOGGER = Logger.getLogger("G3LoginLogoutCliente.View");

	/**
	 * Menu bar.
	 */
	@FXML
	private MenuBar menuBar;

	/**
	 * Menu item for help.
	 */
	@FXML
	private Menu miHelp;

	/**
	 * Menu item for exit.
	 */
	@FXML
	private Menu miExit;

	/**
	 * Table view for trips.
	 */
	@FXML
	private TableView<Trip> tableViewTrips;

	/**
	 * Table column for description.
	 */
	@FXML
	private TableColumn<Trip, String> tableColumnDescription;

	/**
	 * Table column for type.
	 */
	@FXML
	private TableColumn<Trip, String> tableColumnType;

	/**
	 * Table column for start.
	 */
	@FXML
	private TableColumn<Trip, TripInfo> tableColumnStart;

	/**
	 * Table column for end.
	 */
	@FXML
	private TableColumn<Trip, TripInfo> tableColumnEnd;

	/**
	 * Button for print.
	 */
	@FXML
	private Button btPrint;

	/**
	 * Button for purchase or cancel.
	 */
	@FXML
	private Button btPurchaseCancel;

	/**
	 * Combo box for search options.
	 */
	@FXML
	private ComboBox<String> cbSearchOptions;

	/**
	 * Combo box for trip type.
	 */
	@FXML
	private ComboBox<String> cbTripType;

	/**
	 * Radio button for active.
	 */
	@FXML
	private RadioButton rbActive;

	/**
	 * Radio button for inactive.
	 */
	@FXML
	private RadioButton rbInactive;

	/**
	 * Radio button for both.
	 */
	@FXML
	private RadioButton rbBoth;

	/**
	 * Button for search.
	 */
	@FXML
	private Button btSearch;

	/**
	 * Label for status.
	 */
	@FXML
	private Label lbStatus;

	/**
	 * Label for trip type.
	 */
	@FXML
	private Label lbTripType;

	/*
	 * TripManager object 
	 */
	private TripManager tripManager;

	/**
	 * Method to initialize the stage.
	 *
	 * @param root     FXML document graph.
	 * @param customer The customer that is logged in.
	 */
	public void initStage(Parent root) {
		try {
			// Set the window title
			Stage stage = new Stage();
			stage.setTitle("Trips");

			// The window should not be resizable
			stage.setResizable(false);

			// Set the date format pattern based on the user's system language
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

			// Load the Search Options combo with two possible values: "Available Trips" and
			// "My Trips"
			cbSearchOptions.setItems(FXCollections.observableArrayList("Available Trips", "My Trips"));
			// Select "Available Trips" by default
			cbSearchOptions.getSelectionModel().select("Available Trips");

			// Set the "Trip Type" label and the comboBox below it to be invisible
			lbTripType.setVisible(true);
			cbTripType.setVisible(true);

			// The comboBox will have 5 options
			ObservableList<String> tripTypes = FXCollections.observableArrayList("", "Culture", "Nature", "Leisure",
					"Sports");
			cbTripType.setItems(tripTypes);

			// Set the text of the purchase/cancel button to "Purchase Trip" and disable it
			btPurchaseCancel.setText("Purchase Trip");
			btPurchaseCancel.setDisable(true);

			// Add the 3 radio buttons to a ToggleGroup and set invisible
			ToggleGroup radioGroup = new ToggleGroup();
			rbActive.setToggleGroup(radioGroup);
			rbActive.setVisible(false);
			rbInactive.setToggleGroup(radioGroup);
			rbInactive.setVisible(false);
			rbBoth.setToggleGroup(radioGroup);
			rbBoth.setVisible(false);
			lbStatus.setVisible(false);

			// The default button is the Search button
			this.btSearch.setDefaultButton(true);

			// Event handlers
			// Event for closing window
			stage.setOnCloseRequest(this::handleOnActionExit);
			// Event for selection change on combo box Search Options
			cbSearchOptions.setOnAction(this::cbSearchOptionsSelectedItemPropertyChange);
			// Event for menu item Help
			miHelp.setOnAction(this::menuItemHelpOnAction);
			// Event for menu item Exit
			miExit.setOnAction(this::menuItemExitOnAction);
			// Event for button Search
			btSearch.setOnAction(this::btSearchOnAction);
			// Event for button Purchase/Cancel
			btPurchaseCancel.setOnAction(this::btPurchaseCancelOnAction);
			// Event for button Print
			btPrint.setOnAction(this::btPrint);

			// Set editable the table
			tableViewTrips.setEditable(true);
			
			// COnfigure columns
			tableColumnDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            tableColumnType.setCellValueFactory(
                    new PropertyValueFactory<>("type"));
            tableColumnStart.setCellValueFactory(
                    new PropertyValueFactory<>("start date"));
            tableColumnEnd.setCellValueFactory(
                    new PropertyValueFactory<>("end date"));
			//Get instance of TripManager
			tripManager = TripManagerFactory.getTripManager();


			// Show the window
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			LOGGER.severe("Exception: " + e.getMessage());
			this.showErrorAlert(e.getMessage());
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Help menu item
	 * 
	 * @param event An action event.
	 */
	@FXML
	private void menuItemHelpOnAction(ActionEvent event) {
		// Show the Help window as a modal window
		Stage helpStage = new Stage();
		// LOAD HELP WINDOW HERE
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.showAndWait();
	}

	/**
	 * Method to handle the action when the user attempts to exit
	 * 
	 * @param event AN action event.
	 */
	@FXML
	private void menuItemExitOnAction(ActionEvent event) {
		// Display a confirmation message
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES,
				ButtonType.NO);
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				// LOAD LOGIN WINDOW HERE
			}
		});
	}

	/**
	 * Method to handle the action when the user selects an item in the combo box
	 * 
	 * @param event An action event.
	 */
	@FXML
	private void cbSearchOptionsSelectedItemPropertyChange(ActionEvent event) {
		// First, clear the table
		tableViewTrips.getItems().clear();

		if ("Available Trips".equals(cbSearchOptions.getValue())) {
			lbTripType.setVisible(true);
			cbTripType.setVisible(true);
			lbStatus.setVisible(false);
			rbActive.setVisible(false);
			rbInactive.setVisible(false);
			rbBoth.setVisible(false);
			cbTripType.getSelectionModel().select("");
			btPurchaseCancel.setText("Purchase Trip");
			// miCancelTrip.setVisible(false);
			// miPurchaseTrip.setVisible(true);
		} else {
			lbStatus.setVisible(true);
			rbActive.setVisible(true);
			rbInactive.setVisible(true);
			rbBoth.setVisible(true);
			lbTripType.setVisible(false);
			cbTripType.setVisible(false);
			btPurchaseCancel.setText("Cancel Trip");
			// miCancelTrip.setVisible(true);
			// miPurchaseTrip.setVisible(false);
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Search button
	 * 
	 * @param event An action event.
	 */
	@FXML
	private void btSearchOnAction(ActionEvent event) {
		try {
			List<Trip> trips = null;
			String selectedOption = cbSearchOptions.getSelectionModel().getSelectedItem();
			if ("Available Trips".equals(selectedOption)) {
				tableViewTrips.getItems().clear();
				String selectedTripType = cbTripType.getSelectionModel().getSelectedItem();
				if (selectedTripType == null || "".equals(selectedTripType)) {
					trips = tripManager.findAllTrips();
					for (Trip trip : trips) {
						tableViewTrips.getItems().add(trip);


					}
				} else {
					// trips = getTripsByType(selectedTripType);
				}
			} else if ("My Trips".equals(selectedOption)) {
				if (!rbActive.isSelected() && !rbInactive.isSelected() && !rbBoth.isSelected()) {
					throw new Exception("You need to select one Status option");
				}
				tableViewTrips.getItems().clear();
				if (rbActive.isSelected()) {
					// trips = getActiveTripInfoByCustomer(customer);
				} else if (rbInactive.isSelected()) {
					// trips = getInactiveTripInfoByCustomer(customer);
				} else {
					// trips = getAllTripInfoByCustomer(customer);
				}
			}
			if (trips == null) {
				throw new Exception("There aren't any trips to be shown");
			} else {
				// Aquí llenar la tabla con los atributos de los objetos Trip
			}
		} catch (Exception e) {
			// Logger
			LOGGER.severe("Exception. " + e.getMessage());
			this.showErrorAlert(e.getMessage());
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Purchase/Cancel
	 * 
	 * @param event An action event.
	 */
	@FXML
	private void btPurchaseCancelOnAction(ActionEvent event) {
		try {
			String selectedOption = cbSearchOptions.getSelectionModel().getSelectedItem();
			// TripInfo tripInfo;
			if ("Available Trips".equals(selectedOption)) {
				// tripInfo = (TripInfo) tableViewTrips.getSelectionModel().getSelectedItem();
				// deleteTripInfo(tripInfo);
			} else {
				// tripInfo = new TripInfo(selectedTripInfo.getTrip(), customer);
				// createTripInfo(newTripInfo);
			}
		} catch (Exception e) {
			// Logger
			LOGGER.severe("Exception. " + e.getMessage());
			this.showErrorAlert(e.getMessage());
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Print button
	 * 
	 * @param event An action event.
	 */
	@FXML
	private void btPrint(ActionEvent event) {
		//print
	}
     
}