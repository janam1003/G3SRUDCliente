package view.trip;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import view.generic.GenericController;
import entities.Customer;
import entities.EnumTripType;
import static entities.EnumTripType.getEnumTripType;
import entities.Trip;
import entities.TripInfo;
import entities.TripInfoId;
import exception.LogicException;
import factories.TripInfoManagerFactory;
import factories.TripManagerFactory;
import interfaces.TripInfoManager;
import interfaces.TripManager;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    /*
	 * TripInfoManager object
     */
    private TripInfoManager tripInfoManager;
    /*
	 * Customer object
     */
    private Customer customer;

    /**
     * Method to initialize the stage.
     *
     * @param root FXML document graph.
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
            // Event for table view selection change
            tableViewTrips.getSelectionModel().selectedItemProperty().addListener(this::handleSelectedItem);
            // Set editable table
            tableViewTrips.setEditable(true);
            // Configure columns
            tableColumnDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            tableColumnType.setCellValueFactory(
                    new PropertyValueFactory<>("tripType"));

            // Configure editable columns
            Callback<TableColumn<Trip, TripInfo>, TableCell<Trip, TripInfo>> getInitialDatePickerCellFactory = (
                    TableColumn<Trip, TripInfo> param) -> new TripInfoInitialDatePickerTableCell();
            tableColumnStart.setCellFactory(getInitialDatePickerCellFactory);
            // tableColumnStart.setCellValueFactory(cellData
            // ->cellData.getValue().tripInfoProperty());
            tableColumnStart.setOnEditCommit((TableColumn.CellEditEvent<Trip, TripInfo> t) -> {
                //call the method to update the tripInfo only if is my trips
                if ("My Trips".equals(cbSearchOptions.getValue())) {
                    //obtein the object trip that is in the row
                    Trip trip = (Trip) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    //obtein the tripInfo of the trip
                    TripInfo copy = trip.getTripInfo().get(0);
                    //set the new date
                    copy.setInitialDate(t.getNewValue().getInitialDate());
                    //update the tripInfo
                    try {
                        tripInfoManager.updateTripInfo(copy);
                    } catch (LogicException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            tableColumnStart.setOnEditCancel((TableColumn.CellEditEvent<Trip, TripInfo> t) -> {
            });

            // tableColumnStart.setCellValueFactory(cellData -> {
            // Trip trip = Trip.class.cast(cellData.getValue());
            // String formattedDate = "";
            // if (trip.getTripInfo() != null)
            // formattedDate = trip.getTripInfo().get(0).getInitialDate().toString();
            // return new SimpleObjectProperty(formattedDate);
            // });
            // tableColumnEnd.setCellValueFactory(cellData -> {
            // Trip trip = Trip.class.cast(cellData.getValue());
            // String formattedDate = "";
            // if (trip.getTripInfo() != null)
            // formattedDate = trip.getTripInfo().get(0).getLastDate().toString();
            // return new SimpleObjectProperty(formattedDate);
            // });
            Callback<TableColumn<Trip, TripInfo>, TableCell<Trip, TripInfo>> getLastDatePickerCellFactory = (
                    TableColumn<Trip, TripInfo> param) -> new TripInfoLastDatePickerTableCell();
            tableColumnEnd.setCellFactory(getLastDatePickerCellFactory);
            // tableColumnEnd.setCellValueFactory(new PropertyValueFactory<>("lastDate"));
            tableColumnEnd.setOnEditCommit((TableColumn.CellEditEvent<Trip, TripInfo> t) -> {
                try {
                    //obtein the object trip that is in the row
                    Trip trip = (Trip) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    //obtein the tripInfo of the trip
                    TripInfo tripInfo = trip.getTripInfo().get(0);
                    //set the new date
                    tripInfo.setLastDate(t.getNewValue().getLastDate());
                    //Check if initial date is before last date
                    if (tripInfo.getInitialDate().after(tripInfo.getLastDate())) {
                        throw new Exception("Initial date must be before last date");
                    }
                    TripInfoId tripInfoId = new TripInfoId();
                    tripInfoId.setTripId(trip.getId());
                    tripInfoId.setCustomerId(customer.getMail());
                    tripInfo.setTripInfoId(tripInfoId);
                    tripInfo.setCustomer(customer);
                    tripInfo.setTrip(trip);
                    //update the tripInfo
                    tripInfoManager.updateTripInfo(tripInfo);
                } catch (Exception e) {
                    LOGGER.severe("Exception in table on edit commit: " + e.getMessage());
                    this.showErrorAlert(e.getMessage());
                }
            });
            tableColumnEnd.setOnEditCancel((TableColumn.CellEditEvent<Trip, TripInfo> t) -> {
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
     * Method to handle the action when the user selects an item in the combo
     * box
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

    private List<Trip> testgetTripsByType() {
        List<Trip> returnList = new ArrayList<Trip>();
        Trip trip = new Trip();
        trip.setDescription("test");
        trip.setTripType(EnumTripType.CULTURE);
        returnList.add(trip);
        return returnList;
    }

    private List<TripInfo> testTripInfos() {
        List<TripInfo> returnList = new ArrayList<TripInfo>();
        TripInfo tripInfo = new TripInfo();
        Date initialDate = new Date();
        Date lastDate = new Date();
        initialDate.setTime(1546300800);
        lastDate.setTime(1546387200);
        tripInfo.setInitialDate(initialDate);
        tripInfo.setLastDate(lastDate);
        Trip trip = new Trip();
        trip.setDescription("test");
        trip.setTripType(EnumTripType.CULTURE);
        tripInfo.setTrip(trip);
        // 2nd tripinfo
        TripInfo tripInfo2 = new TripInfo();
        Date initialDate2 = new Date();
        Date lastDate2 = new Date();
        initialDate2.setTime(1646300800);
        lastDate2.setTime(1646387200);
        tripInfo2.setInitialDate(initialDate2);
        tripInfo2.setLastDate(lastDate2);
        Trip trip2 = new Trip();
        trip2.setDescription("test2");
        trip2.setTripType(EnumTripType.CULTURE);
        tripInfo2.setTrip(trip2);
        returnList.add(tripInfo2);
        returnList.add(tripInfo);
        return returnList;
    }

    /**
     * Method to handle the action when the user clicks on the Search button
     *
     * @param event An action event.
     */
    @FXML
    private void btSearchOnAction(ActionEvent event) {
        try {
            String selectedOption = cbSearchOptions.getSelectionModel().getSelectedItem();
            List<Trip> trips = null;
            if ("Available Trips".equals(selectedOption)) {
                tableViewTrips.getItems().clear();
                int index = cbTripType.getSelectionModel().getSelectedIndex();
                if (index == -1 || index == 0) {
                    trips = tripManager.findAllTrips();
                    //trips = testgetTripsByType();
                } else 
                {
                    trips = tripManager.findTripsByTripType(getEnumTripType(cbTripType.getSelectionModel().getSelectedIndex()));
                    //trips = testgetTripsByType();
                }
                if (trips == null) {
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
                    //tripInfos = testTripInfos();
                } else if (rbInactive.isSelected())
                    tripInfos = tripInfoManager.findInactiveTripInfoByCustomer(customer);
                else
                    tripInfos = tripInfoManager.findAllTripInfoByCustomer(customer);
                if (tripInfos == null)
                    throw new Exception("There aren't any trips to be shown");
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
            Trip trip = tableViewTrips.getSelectionModel().getSelectedItem();
            TripInfo tripInfo = new TripInfo();
            tripInfo.setTrip(trip);
            tripInfo.setCustomer(customer);
            TripInfoId tripInfoId = new TripInfoId();
            tripInfoId.setTripId(trip.getId());
            tripInfoId.setCustomerId(customer.getMail());
            tripInfo.setTripInfoId(tripInfoId);
            if ("Available Trips".equals(selectedOption)) {
                //chek if both dates are not null
                if (trip.getTripInfo().get(0).getInitialDate() == null || trip.getTripInfo().get(0).getLastDate() == null) {
                    throw new Exception("You need to select both dates");
                }
                //Check if initial date is before last date
                if (trip.getTripInfo().get(0).getInitialDate().after(trip.getTripInfo().get(0).getLastDate())) {
                    throw new Exception("Initial date must be before last date");
                }
                tripInfo.setInitialDate(trip.getTripInfo().get(0).getInitialDate());
                tripInfo.setLastDate(trip.getTripInfo().get(0).getLastDate());
                tripInfoManager.createTripInfo(tripInfo);
            } else {
                tripInfoManager.deleteTripInfo(tripInfo);
            }
        } catch (Exception e) {
            // Logger
            LOGGER.severe("Exception. " + e.getMessage());
            showErrorAlert(e.getMessage());
        }
    }

    private void handleSelectedItem(Observable event) {
        if (tableViewTrips.getSelectionModel().getSelectedItem() != null) {
            // Enable edit and delete buttons and menu items
            btPurchaseCancel.setDisable(false);
            // miEdit.setDisable(false);
            // miDelete.setDisable(false);
        } else {
            // Disable edit and delete buttons and menu items
            btPurchaseCancel.setDisable(true);
            // miEdit.setDisable(true);
            // miDelete.setDisable(true);
        }
    }

    /**
     * Method to handle the action when the user clicks on the Print button
     *
     * @param event An action event.
     */
    @FXML
    private void btPrint(ActionEvent event) {
        // print
    }

}
