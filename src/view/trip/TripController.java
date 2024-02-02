package view.trip;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import view.generic.GenericController;
import view.signin.LoginController;
import entities.Customer;
import static entities.EnumTripType.getEnumTripType;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import exception.LogicException;
import factories.TripInfoManagerFactory;
import factories.TripManagerFactory;
import interfaces.TripInfoManager;
import interfaces.TripManager;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.signin.LoginController;

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
	private Menu menuExit;

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
	private TableColumn<Trip, LocalDate> tableColumnStart;

	/**
	 * Table column for end.
	 */
	@FXML
	private TableColumn<Trip, LocalDate> tableColumnEnd;

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
	/**
	 * MenuItem for book trip
	 */
	@FXML
	private MenuItem menuItemBook;
	/**
	 * MenuItem for cancel trip
	 */
	@FXML
	private MenuItem menuItemCancel;
	/**
	 * TripManager object
	 */
	private TripManager tripManager;
	/**
	 * TripInfoManager object
	 */
	private TripInfoManager tripInfoManager;
	/**
	 * Customer object
	 */
	private Customer customer;
	/**
	 * Date object to store previous start date value
	 */
	private LocalDate oldStartDate;
	/**
	 * Date object to store previous end date value
	 */
	private LocalDate oldEndDate;
	/**
	 * Private list of tripInfos to NOT show user cause alredy booked trips
	 */
	private List<TripInfo> tripInfosBooked;

	/**
	 * Method to initialize the stage.
	 *
	 * @param root     FXML document graph.
	 * @param customer The customer that is logged in.
	 */
	public void initStage(Parent root, Customer loggedCustomer) {
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
			// Set the radio button "Active" as selected by default
			rbActive.setSelected(true);

			// Set the menu item cancel to invisible
			menuItemCancel.setVisible(false);
			// Set disable both menu items
			menuItemCancel.setDisable(true);
			menuItemBook.setDisable(true);

			// The default button is the Search button
			this.btSearch.setDefaultButton(true);

			// Event handlers
			// Event for closing window
			stage.setOnCloseRequest(this::handleOnActionExit);
			// Event for selection change on combo box Search Options
			cbSearchOptions.setOnAction(this::cbSearchOptionsSelectedItemPropertyChange);
			// Event for menu item Help
			miHelp.setOnAction(this::menuItemHelpOnAction);
			// Add menu item to menubar menu so miExit can fire an action event
			MenuItem miExit = new MenuItem("Exit");
			menuExit.getItems().add(miExit);
			miExit.setOnAction(this::menuItemExitOnAction);
			// Event for button Search
			btSearch.setOnAction(this::btSearchOnAction);
			// Event for button Purchase/Cancel
			btPurchaseCancel.setOnAction(this::btPurchaseCancelOnAction);
			// Event for button Print
			btPrint.setOnAction(this::btPrint);
			// Event for table view selection change
			tableViewTrips.getSelectionModel().selectedItemProperty().addListener(this::handleSelectedItem);
			// Event for menu item book
			menuItemBook.setOnAction(this::btPurchaseCancelOnAction);
			// Event for menu item cancel
			menuItemCancel.setOnAction(this::btPurchaseCancelOnAction);
			// Set editable table
			tableViewTrips.setEditable(true);
			// Configure columns
			tableColumnDescription.setCellValueFactory(
					new PropertyValueFactory<>("description"));
			tableColumnType.setCellValueFactory(
					new PropertyValueFactory<>("tripType"));

			// Configure editable columns
			Callback<TableColumn<Trip, LocalDate>, TableCell<Trip, LocalDate>> startDatePickerCellFactory = (
					TableColumn<Trip, LocalDate> param) -> new DatePickerTableCell();
			tableColumnStart.setCellFactory(startDatePickerCellFactory);
			tableColumnStart.setCellValueFactory(factory -> {
				return getTripInfoToLocalDateInitialDateValueFactory(factory);
			});
			tableColumnStart.setOnEditCommit((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				try {
					// obtein the object trip that is in the row
					Trip trip = (Trip) t.getTableView().getItems().get(t.getTablePosition().getRow());
					Date initialDate = Date.from(t.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					// call the method to update the tripInfo only if is my trips
					if ("My Trips".equals(cbSearchOptions.getValue())) {
						// obtein the tripInfo of the trip
						TripInfo copy = trip.getTripInfo().get(0);
						// Check if initial date is before last date
						if (initialDate.after(copy.getLastDate())) {
							throw new Exception("Initial date must be before last date");
						}
						// set the new date
						copy.setInitialDate(initialDate);
						// update the tripInfo
						tripInfoManager.updateTripInfo(copy);
					} else {
						if (trip.getTripInfo() == null) {
							TripInfo tripInfo = new TripInfo();
							tripInfo.setTrip(trip);
							tripInfo.setCustomer(customer);
							tripInfo.setInitialDate(initialDate);
							trip.setTripInfo(new ArrayList<TripInfo>());
							trip.getTripInfo().add(tripInfo);
						} else {
							trip.getTripInfo().get(0).setInitialDate(initialDate);
						}
					}
				} catch (Exception e) {
					LOGGER.severe("Exception in table on edit commit: . " + e);
					// Restore old value
					t.getRowValue().getTripInfo().get(0)
							.setInitialDate(Date.from(oldStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					tableViewTrips.refresh();
					this.showErrorAlert(e.getMessage());
				}
			});
			tableColumnStart.setOnEditStart((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				oldStartDate = t.getOldValue();
			});
			Callback<TableColumn<Trip, LocalDate>, TableCell<Trip, LocalDate>> lastDatePickerCellFactory = (
					TableColumn<Trip, LocalDate> param) -> new DatePickerTableCell();
			tableColumnEnd.setCellFactory(lastDatePickerCellFactory);
			tableColumnEnd.setCellValueFactory(factory -> {
				return getTripInfoToLocalDateLastDateValueFactory(factory);
			});
			tableColumnEnd.setOnEditCommit((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				try {
					// obtein the object trip that is in the row
					Trip trip = (Trip) t.getTableView().getItems().get(t.getTablePosition().getRow());
					// Obtein the date to change
					Date lastDate = Date.from(t.getNewValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					// call the method to update the tripInfo only if is my trips
					if ("My Trips".equals(cbSearchOptions.getValue())) {
						// obtein the tripInfo of the trip
						TripInfo copy = trip.getTripInfo().get(0);
						// Check if initial date is before last date
						if (copy.getInitialDate().after(lastDate)) {
							throw new Exception("Last date must be after initial date");
						}
						// set the new date
						copy.setLastDate(lastDate);
						// update the tripInfo
						tripInfoManager.updateTripInfo(copy);

					} else {
						if (trip.getTripInfo() == null) {
							TripInfo tripInfo = new TripInfo();
							tripInfo.setTrip(trip);
							tripInfo.setCustomer(customer);
							tripInfo.setLastDate(lastDate);
							trip.setTripInfo(new ArrayList<TripInfo>());
							trip.getTripInfo().add(tripInfo);
						} else {
							trip.getTripInfo().get(0).setLastDate(lastDate);
						}
					}
				} catch (Exception e) {
					LOGGER.severe("Exception in table on edit commit: " + e.getMessage());
					// Restore old value
					t.getRowValue().getTripInfo().get(0)
							.setLastDate(Date.from(oldEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					this.showErrorAlert(e.getMessage());
					tableViewTrips.refresh();
				}
			});
			tableColumnStart.setOnEditCancel((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				// Put the old value on the datepicker cell
			});
			tableColumnEnd.setOnEditCancel((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				t.getRowValue().getTripInfo().get(0)
						.setLastDate(Date.from(oldEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			});
			tableColumnEnd.setOnEditStart((TableColumn.CellEditEvent<Trip, LocalDate> t) -> {
				oldEndDate = t.getOldValue();
			});

			// Get instance of TripManager
			tripManager = TripManagerFactory.getTripManager();
			// Get instance of TripInfoManager
			tripInfoManager = TripInfoManagerFactory.getTripInfoManager();
			// Get the customer that is logged in
			this.customer = loggedCustomer;
			// Show the window
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			LOGGER.severe("Exception in initStage: " + e.getMessage());
			this.showErrorAlert(e.getMessage());
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Help menu item
	 *
	 * @param event An action event.
	 */
	private void menuItemHelpOnAction(ActionEvent event) {
		// Show the Help window as a modal window
		// Stage helpStage = new Stage();
		// LOAD HELP WINDOW HERE
		// helpStage.initModality(Modality.APPLICATION_MODAL);
		// helpStage.showAndWait();
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
			try {
				if (response == ButtonType.YES) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signin/Login.fxml"));
					Parent root = (Parent) loader.load();
					// Get the controller instance from the loader
					LoginController loginController = loader.getController();
					Stage newStage = new Stage();
					newStage.setScene(new Scene(root));
					// Get the current stage from any element on the current window
					Stage currentStage = (Stage) cbSearchOptions.getScene().getWindow(); // menuItemExit is the MenuItem that triggered the event
					
					// Show the new stage
					newStage.show();
					
					// Close the current stage
					currentStage.close();
				}
			} catch (Exception e) {
				LOGGER.severe("Exception in menuItemExitOnAction: " + e.getMessage());
				this.showErrorAlert(e.getMessage());
			}
		});
	}

	/**
	 * Method to handle the action when the user selects an item in the combo
	 * box
	 *
	 * @param event An action event.
	 */
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
			menuItemCancel.setVisible(false);
			menuItemBook.setVisible(true);
		} else {
			lbStatus.setVisible(true);
			rbActive.setVisible(true);
			rbInactive.setVisible(true);
			rbActive.setSelected(true);
			rbBoth.setVisible(true);
			lbTripType.setVisible(false);
			cbTripType.setVisible(false);
			btPurchaseCancel.setText("Cancel Trip");
			menuItemCancel.setVisible(true);
			menuItemBook.setVisible(false);
		}
	}

	/**
	 * Method to obtain only not booked trips
	 *
	 * @param trips List of trips to filter
	 * @return List of trips not booked
	 * @throws Exception
	 */
	private List<Trip> obtainOnlyNotBookedTrips(List<Trip> trips) throws Exception {
		try {
			if (tripInfosBooked == null) {
				tripInfosBooked = tripInfoManager.findAllTripInfoByCustomer(customer);
			}
			List<Trip> tripsNotBooked = new ArrayList<Trip>();
			for (Trip trip : trips) {
				boolean booked = false;
				for (TripInfo tripInfo : tripInfosBooked) {
					if (tripInfo.getTrip().getId() == trip.getId()) {
						booked = true;
						break;
					}
				}
				if (!booked) {
					tripsNotBooked.add(trip);
				}
			}
			return tripsNotBooked;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Search button
	 *
	 * @param event An action event.
	 */
	private void btSearchOnAction(ActionEvent event) {
		try {
			String selectedOption = cbSearchOptions.getSelectionModel().getSelectedItem();
			List<Trip> trips = null;
			if ("Available Trips".equals(selectedOption)) {
				tableViewTrips.getItems().clear();
				int index = cbTripType.getSelectionModel().getSelectedIndex();
				if (index == -1 || index == 0) {
					trips = tripManager.findAllTrips();
				} else {
					trips = tripManager
							.findTripsByTripType(getEnumTripType(cbTripType.getSelectionModel().getSelectedIndex()));
				}
				trips = obtainOnlyNotBookedTrips(trips);
				if (trips == null || trips.isEmpty()) {
					throw new Exception("There aren't any trips to be shown");
				}
				ObservableList<Trip> observableList = FXCollections.observableArrayList(trips);
				tableViewTrips.setItems(observableList);
			} else if ("My Trips".equals(selectedOption)) {
				List<TripInfo> tripInfos = null;
				if (!rbActive.isSelected() && !rbInactive.isSelected() && !rbBoth.isSelected()) {
					throw new Exception("You need to select one Status option");
				}
				tableViewTrips.getItems().clear();
				if (rbActive.isSelected()) {
					tripInfos = tripInfoManager.findActiveTripInfoByCustomer(customer);
				} else if (rbInactive.isSelected()) {
					tripInfos = tripInfoManager.findInactiveTripInfoByCustomer(customer);
				} else {
					tripInfos = tripInfoManager.findAllTripInfoByCustomer(customer);
				}
				if (tripInfos == null || tripInfos.isEmpty()) {
					throw new Exception("There aren't any booked trips");
				}
				// Se crear una lista de trips a partir de la lista de tripinfos
				trips = new ArrayList<Trip>();
				for (TripInfo ti : tripInfos) {
					ti.getTrip().setTripInfo(new ArrayList<TripInfo>());
					Trip trip = ti.getTrip();
					trip.getTripInfo().add(ti);
					trips.add(trip);
				}
				tableViewTrips.getItems().setAll(trips);
			}
		} catch (Exception e) {
			// Logger
			LOGGER.severe("Exception. " + e);
			if (e.getMessage().isEmpty()) {
				this.showErrorAlert("Error while doing search");
			} else {
				this.showErrorAlert(e.getMessage());
			}
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Purchase/Cancel
	 *
	 * @param event An action event.
	 */
	private void btPurchaseCancelOnAction(ActionEvent event) {
		try {
			String selectedOption = cbSearchOptions.getSelectionModel().getSelectedItem();
			Trip trip = tableViewTrips.getSelectionModel().getSelectedItem();
			if (trip.getTripInfo() == null) {
				throw new Exception("You need to select desired dates for the trip");
			}
			if ("Available Trips".equals(selectedOption)) {
				// get the initial and end date from the table
				Date initialDate = trip.getTripInfo().get(0).getInitialDate();
				Date lastDate = trip.getTripInfo().get(0).getLastDate();
				TripInfo tripInfo = new TripInfo();
				tripInfo.setTrip(trip);
				tripInfo.setCustomer(customer);
				TripInfoId tripInfoId = new TripInfoId();
				tripInfoId.setTripId(trip.getId());
				tripInfoId.setCustomerId(customer.getMail());
				tripInfo.setTripInfoId(tripInfoId);
				// chek if both dates are not null
				if (initialDate == null || lastDate == null || initialDate.equals("") || lastDate.equals("")) {
					throw new Exception("You need to select start and end dates");
				}
				// Check if initial date is before last date
				if (initialDate.after(lastDate)) {
					throw new Exception("Initial date must be before last date");
				}
				tripInfo.setInitialDate(initialDate);
				tripInfo.setLastDate(lastDate);
				// Show confirmation alert
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to purchase this trip?",
						ButtonType.YES,
						ButtonType.NO);
				alert.showAndWait().ifPresent(response -> {

					if (response == ButtonType.YES) {
						try {
							tripInfoManager.createTripInfo(tripInfo);
                                                        tripInfosBooked.add(tripInfo);
							showInfoAlert("Trip purchased successfully");
							// Llamar al metodo de search para que se actualice la tabla
							btSearchOnAction(event);
						} catch (LogicException ex) {
							Logger.getLogger(TripController.class.getName()).log(Level.SEVERE, null, ex);
						}

					}
				});
			} else {
				// Show confirmation alert
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this trip?",
						ButtonType.YES,
						ButtonType.NO);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.YES) {
						try {
							tripInfoManager.deleteTripInfo(trip.getTripInfo().get(0));
							showInfoAlert("Trip canceled successfully");
							// Delete from table and refresh
							tableViewTrips.getItems().remove(trip);
							tableViewTrips.refresh();
						} catch (LogicException ex) {
							Logger.getLogger(TripController.class.getName()).log(Level.SEVERE, null, ex);
						}

					}
				});
			}
		} catch (Exception e) {
			// Logger
			LOGGER.severe("Exception. " + e.getMessage());
			showErrorAlert("Error with the selected trip: " + e.getMessage());
		}
	}
	/**
	 * Method to handle the action when the user selects a row
	 * @param event An action event.
	 */
	private void handleSelectedItem(Observable event) {
		if (tableViewTrips.getSelectionModel().getSelectedItem() != null) {
			// Enable edit and delete buttons and menu items
			btPurchaseCancel.setDisable(false);
			menuItemCancel.setDisable(false);
			menuItemBook.setDisable(false);
		} else {
			// Disable edit and delete buttons and menu items
			btPurchaseCancel.setDisable(true);
			menuItemCancel.setDisable(true);
			menuItemBook.setDisable(true);
		}
	}

	/**
	 * Method to handle the action when the user clicks on the Print button
	 *
	 * @param event An action event.
	 */
	private void btPrint(ActionEvent event) {
		try {
			// Convert the trip list from table to tripInfo list
			List<TripInfo> tripInfos = new ArrayList<TripInfo>();
			for (Trip trip : tableViewTrips.getItems()) {
				TripInfo tripInfo = new TripInfo();
				if (trip.getTripInfo() != null)
					tripInfo = trip.getTripInfo().get(0);
				tripInfo.setTrip(trip);
				tripInfos.add(tripInfo);
			}
			// Load the Jasper report from its path
			JasperReport jasperReport = JasperCompileManager.compileReport("src/view/trip/TripReport.jrxml");
			// Create a map to pass any parameters to the report
			Map<String, Object> parameters = new HashMap<>();
			// Convert the data to JRBeanCollectionDataSource as JasperReport accepts data
			// in this format
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tripInfos);
			// Fill the report with data
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setVisible(true);
		} catch (JRException ex) {
			showErrorAlert("Error while printing the report: " + ex.getMessage());
			Logger.getLogger(TripController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	/**
	 * Method to get the tripInfo to localDate initial date value factory
	 * @param factory Factory to get the value
	 * @return ObservableValue<LocalDate> with the value
	 */
	private ObservableValue<LocalDate> getTripInfoToLocalDateInitialDateValueFactory(
			CellDataFeatures<Trip, LocalDate> factory) {
		if (cbSearchOptions.getSelectionModel().getSelectedItem().equalsIgnoreCase("My trips")) {
			return new SimpleObjectProperty<LocalDate>(factory.getValue().getTripInfo().get(0).getInitialDate()
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		return null;
	}
	/**
	 * Method to get the tripInfo to localDate initial date value factory
	 * @param factory Factory to get the value
	 * @return ObservableValue<LocalDate> with the value
	 */
	private ObservableValue<LocalDate> getTripInfoToLocalDateLastDateValueFactory(
			CellDataFeatures<Trip, LocalDate> factory) {
		if (cbSearchOptions.getSelectionModel().getSelectedItem().equalsIgnoreCase("My trips")) {
			return new SimpleObjectProperty<LocalDate>(factory.getValue().getTripInfo().get(0).getLastDate()
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		return null;
	}

}
