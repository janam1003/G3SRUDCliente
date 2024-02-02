/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import entities.Customer;
import entities.EnumUserType;
import exception.CreateException;
import exception.CredentialException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import factories.CustomerManagerFactory;
import interfaces.CustomerManager;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.generic.GenericController;

/**
 * FXML Controller class
 *
 * @author danid
 */
public class CustomerListController extends GenericController {

    private static final Logger LOGGER = Logger.getLogger(CustomerListController.class.getName());

    private ObservableList<Customer> customers;

    @FXML
    private Menu menuCustomers;
    @FXML
    private Menu menuTrips;
    @FXML
    private Menu menuExit;
    @FXML
    private Menu menuHelp;
    @FXML
    private TextField tfMail;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnNewCustomer;
    @FXML
    private Button btnShowTrips;
    @FXML
    private TableView<Customer> tvCustomers;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrintDocument;
    @FXML
    private ComboBox<String> cbSearchFilters;
    @FXML
    private TableColumn<Customer, String> tcName;
    @FXML
    private TableColumn<Customer, String> tcMail;
    @FXML
    private TableColumn<Customer, String> tcPhone;
    @FXML
    private TableColumn<Customer, String> tcAddress;
    @FXML
    private TableColumn<Customer, String> tcZip;
    @FXML
    private TableColumn<Customer, Date> tcCreationDate;
    @FXML
    private MenuItem cmmiAdd;
    @FXML
    private MenuItem cmmiDelete;
    @FXML
    private MenuItem cmmiPrint;
    @FXML
    private MenuItem cmmiHelp;

    CustomerManager mang = CustomerManagerFactory.getCustomerManager();

    /**
     * Initializes the JavaFX stage with the specified root Parent node.
     *
     * @param root The root Parent node for the JavaFX stage.
     */
    public void initStage(Parent root) {

        LOGGER.info("Initializing the view");
        try {
            // Create a new Scene with the specified root node
            Scene scene = new Scene(root);

            // Configure the stage properties
            stage.setTitle("Customer List");
            stage.setResizable(false);

            // Set default button and initial states for various buttons
            btnNewCustomer.setDefaultButton(true);
            btnDelete.setDisable(true);
            btnShowTrips.setDisable(true);
            btnSearch.setDisable(true);
            cmmiDelete.setDisable(true);

            // Set items and default value for the search filters ComboBox
            cbSearchFilters.setItems(FXCollections.observableArrayList("Every Customer", "Customer with trips", "Customers with one week trips"));
            cbSearchFilters.setValue("Every Customer");

            // Make the TableView editable and configure its columns with PropertyValueFactory
            tvCustomers.setEditable(true);
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            tcZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
            tcCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

            // Load data into the TableView using getAllCustomers()
            customers = FXCollections.observableArrayList(mang.findAllCustomers());
            // Populate the TableView with data obtained from getAllCustomers()
            tvCustomers.setItems(customers);
            tvCustomers.setEditable(true);

            /**
             * Configures the "Name" column of a TableView for editing using
             * TextField. - Cell factory: TextFieldTableCell for Customer type.
             * - Cell value factory: PropertyValueFactory for "Name" property of
             * Customer. - On edit commit, it validates the new value and
             * updates the Customer object accordingly.
             *
             */
            tcName.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("Name"));
            tcName.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    // Validate the new value
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Name cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Name cannot be empty");
                    } else if (validateField(t.getNewValue(), namePattern)) {
                        throw new CredentialException("Make sure that the Name is well written");
                    } else {
                        // Update the Customer object with the new value
                        customer.setName(t.getNewValue());
                    }
                    // Update the data source (mang) with the modified Customer
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    // Handle UpdateException by reverting the changes and showing an error alert
                    customer.setName(t.getOldValue());
                    this.showErrorAlert("Unable to modify the name");
                    LOGGER.severe("Unable to modify the name");
                } catch (CredentialException e) {
                    // Handle CredentialException by reverting the changes and showing an error alert
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } finally {
                    // Refresh the TableView to reflect any changes
                    tvCustomers.refresh();
                }
            });

            /**
             * Configures the email column in the TableView for editing and
             * handling edit events. Uses TextFieldTableCell for editing, sets
             * cell value factory, and defines edit commit behavior. Validates
             * and updates customer email, handling exceptions appropriately.
             *
             */
            tcMail.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcMail.setCellValueFactory(
                    new PropertyValueFactory<>("Mail"));
            tcMail.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    String oldMail;
                    // Validation checks for the new email value
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Mail cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Mail cannot be empty");
                    } else if (validateField(t.getNewValue(), mailPattern)) {
                        throw new CredentialException("Make sure that the mail is well written");
                    } else if (mang.findCustomerByMail(t.getNewValue()) != null) {
                        throw new CredentialException("This mail is already in use");
                    } else {
                        oldMail = t.getOldValue();
                        customer.setMail(t.getNewValue());
                    }
                    // Update the customer with the new email and delete the old email
                    mang.updateCustomer(customer, true);
                    mang.deleteCustomer(oldMail);

                } catch (UpdateException e) {
                    // Handle update exception
                    customer.setMail(t.getOldValue());
                    this.showErrorAlert("Unable to modify the mail");
                    LOGGER.severe("Unable to modify the name");
                } catch (CredentialException e) {
                    // Handle credential exception
                    customer.setMail(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } catch (DeleteException ex) {
                    // Handle delete exception
                    customer.setMail(t.getOldValue());
                    this.showErrorAlert("Error connecting to the server");
                    LOGGER.severe("Error connecting to the server");
                } catch (ReadException ex) {
                    // Handle read exception
                    Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // Refresh the TableView after editing
                    tvCustomers.refresh();
                }
            });

            /**
             * Configures the Address TableColumn with cell factory, cell value
             * factory, and edit commit handler. Allows editing of customer
             * addresses in a TableView.
             */
            tcAddress.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

            tcAddress.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                // Retrieve the customer from the edited row
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));

                try {
                    // Check if the new address is valid
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Address cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Address cannot be empty");
                    } else {
                        // Update the customer's address
                        customer.setAddress(t.getNewValue());
                    }

                    // Update the customer in the data source
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    // Handle exception when unable to update the customer
                    customer.setAddress(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Address");
                    LOGGER.severe("Unable to modify the Address");
                } catch (CredentialException e) {
                    // Handle exception when the new address is invalid
                    customer.setAddress(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } finally {
                    // Refresh the TableView to reflect the changes
                    tvCustomers.refresh();
                }
            });

            /**
             * Configures the Zip code column in the table view. Uses a
             * TextFieldTableCell for editing and sets up cell value factory.
             * Handles edit commit events to validate and update the Zip code.
             *
             */
            tcZip.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcZip.setCellValueFactory(
                    new PropertyValueFactory<>("Zip"));
            tcZip.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));

                try {
                    // Validate and update Zip code
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Zip cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Zip cannot be empty");
                    } else if (validateField(t.getNewValue(), zipPattern)) {
                        throw new CredentialException("Make sure that the zip is well written");
                    } else {
                        customer.setZip(t.getNewValue());
                    }

                    // Update customer and refresh table view
                    mang.updateCustomer(customer, true);
                } catch (UpdateException e) {
                    // Handle update exception
                    customer.setZip(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Zip");
                    LOGGER.severe("Unable to modify the Zip");
                } catch (CredentialException e) {
                    // Handle credential exception
                    customer.setZip(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } finally {
                    // Refresh the table view
                    tvCustomers.refresh();
                }
            });

            /**
             * Configures the cell factory, cell value factory, and edit commit
             * event for the "Phone" column in the TableView. The cell factory
             * is set to use TextFieldTableCell for editing. The cell value
             * factory is set to use PropertyValueFactory for mapping to the
             * "Phone" property in the Customer class. The edit commit event is
             * handled to update the customer's phone, perform validation, and
             * update the TableView accordingly.
             *
             */
            tcPhone.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcPhone.setCellValueFactory(
                    new PropertyValueFactory<>("Phone"));
            tcPhone.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    // Perform validation on the new phone value
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Phone cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Phone cannot be empty");
                    } else if (validateField(t.getNewValue(), phonePattern)) {
                        throw new CredentialException("Make sure that the phone is well written");
                    } else {
                        // Update customer's phone if validation passes
                        customer.setPhone(t.getNewValue());
                    }
                    // Update the customer in the data manager
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    // Handle exception if update fails, revert to the old phone value
                    customer.setPhone(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Phone");
                    LOGGER.severe("Unable to modify the Phone");
                } catch (CredentialException e) {
                    // Handle exception if validation fails, revert to the old phone value
                    customer.setPhone(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } finally {
                    // Refresh the TableView to reflect the changes
                    tvCustomers.refresh();
                }
            });

            /**
             * Callback for creating a custom cell factory with a DatePicker for
             * editing Date values in a TableColumn.
             */
            Callback<TableColumn<Customer, Date>, TableCell<Customer, Date>> dateCellFactory;

// Set the cell factory for the "creationDate" TableColumn
            tcCreationDate.setCellFactory(getDatePickerCellFactory());

// Set the cell value factory for the "creationDate" TableColumn
            tcCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

// Set the onEditCommit handler for the "creationDate" TableColumn
            tcCreationDate.setOnEditCommit((TableColumn.CellEditEvent<Customer, Date> t) -> {
                // Get the edited customer
                Customer customer = ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow()));

                try {
                    // Validate the new date value
                    if (t.getNewValue().toString().length() > 254) {
                        throw new CredentialException("The date cannot have more than 254 characters");
                    } else if (t.getNewValue() == null) {
                        throw new CredentialException("Date cannot be empty");
                    } else if (t.getNewValue().after(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                        throw new CredentialException("Date must be before the actual date");
                    } else {
                        // Update the customer's creation date
                        customer.setCreationDate(t.getNewValue());
                    }

                    // Update the customer in the data model
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    // Handle update exception
                    customer.setCreationDate(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Phone");
                    LOGGER.severe("Unable to modify the Phone");
                } catch (CredentialException e) {
                    // Handle credential exception
                    customer.setCreationDate(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                    LOGGER.severe(e.getMessage());
                } finally {
                    // Refresh the TableView
                    tvCustomers.refresh();
                }
            });

            // New Customer actions
            btnNewCustomer.setOnAction(this::handleNewAction);
            cmmiAdd.setOnAction(this::handleNewAction);

            // Delete Customer actions
            btnDelete.setOnAction(this::handleDeleteAction);
            cmmiDelete.setOnAction(this::handleDeleteAction);

            // Search action
            btnSearch.setOnAction(this::handleSearchAction);

            // Exit action
            stage.setOnCloseRequest(this::handleOnActionExit);

            // ComboBox change listener
            cbSearchFilters.getSelectionModel().selectedItemProperty().addListener(this::handleOnComboBoxChange);

            // Text Field property change listener
            tfMail.textProperty().addListener(this::handleTestFielPropertyChange);

            // Table View selection change listener
            tvCustomers.getSelectionModel().selectedItemProperty().addListener(this::handleSelectRowChange);

            // Print action
            cmmiPrint.setOnAction(this::handlePrintOnAction);
            btnPrintDocument.setOnAction(this::handlePrintOnAction);

            // Not developed additions
            btnShowTrips.setOnAction(this::handleStillDev);
            menuHelp.setOnAction(this::handleStillDev);
            menuCustomers.setOnAction(this::handleStillDev);
            menuExit.setOnAction(this::handleStillDev);
            menuTrips.setOnAction(this::handleStillDev);
            cmmiHelp.setOnAction(this::handleStillDev);

            // Set the scene and show the stage
            stage.setScene(scene);
            stage.show();

        } catch (ReadException ex) {
            // Log an error if there's an exception during setup
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Handles the change event when the selection in a ComboBox changes.
     *
     * @param observable The ObservableValue representing the ComboBox's value.
     * @param oldValue The previous value before the change.
     * @param newValue The new value after the change.
     */
    private void handleOnComboBoxChange(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            switch (newValue.toString()) {
                case "Every Customer":
                    tfMail.setText("");
                    customers = FXCollections.observableArrayList(mang.findAllCustomers());
                    tvCustomers.setItems(customers);
                    tvCustomers.refresh();
                    break;
                case "Customer with trips":
                    tfMail.setText("");
                    customers = FXCollections.observableArrayList(mang.findCustomersWithTrips());
                    tvCustomers.setItems(customers);
                    tvCustomers.refresh();
                    break;
                case "Customers with one week trips":
                    tfMail.setText("");
                    customers = FXCollections.observableArrayList(mang.findOneWeekTrips());
                    tvCustomers.setItems(customers);
                    tvCustomers.refresh();
                    break;
            }
        } catch (ReadException e) {
            this.showErrorAlert("Error getting the stored customers");
            LOGGER.severe("Error getting the stored customers");
        }
    }

    /**
     * Handles the change event when the property of a TextField changes.
     *
     * @param observable The ObservableValue representing the TextField's value.
     * @param oldValue The previous value before the change.
     * @param newValue The new value after the change.
     */
    private void handleTestFielPropertyChange(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.isEmpty()) {
            btnSearch.setDisable(false);
        } else {
            btnSearch.setDisable(true);
        }
    }

    /**
     * Handles the change event when the selection of a row changes in a
     * TableView.
     *
     * @param observable The ObservableValue representing the selected row in
     * the TableView.
     * @param oldValue The previous selected row before the change.
     * @param newValue The new selected row after the change.
     */
    private void handleSelectRowChange(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnDelete.setDisable(false);
            cmmiDelete.setDisable(false);
            btnShowTrips.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnShowTrips.setDisable(true);
            cmmiDelete.setDisable(true);
        }
    }

    /**
     * Handles the delete action for the selected customer in the TableView.
     *
     * @param event The ActionEvent triggering the method.
     */
    public void handleDeleteAction(ActionEvent event) {
        try {
            // Get the selected customer from the TableView
            Customer userToDelete = (Customer) tvCustomers.getSelectionModel().getSelectedItem();

            // Delete the customer from the data source
            mang.deleteCustomer(userToDelete.getMail());

            // Remove the customer from the local ObservableList
            customers.remove(userToDelete);

            // Clear the selection and refresh the TableView
            tvCustomers.getSelectionModel().clearSelection();
            tvCustomers.refresh();
        } catch (DeleteException e) {
            // Handle the exception by displaying an error alert
            this.showErrorAlert("Unable to Delete the selected User");
            LOGGER.severe("Unable to Delete the selected User");
        }
    }

    /**
     * Handles the creation of a new customer.
     *
     * @param event The ActionEvent triggering the method.
     */
    public void handleNewAction(ActionEvent event) {
        Random mail = new Random();

        try {
            // Create a new customer with default values
            Customer customer = new Customer();
            customer.setName(null);
            customer.setPassword(null);
            customer.setCreationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            customer.setAddress(null);
            customer.setPassword("example");
            customer.setMail("example" + Math.abs(mail.nextInt()) + "@gmail.com");
            customer.setPhone(null);
            customer.setZip(null);
            customer.setUserType(EnumUserType.CUSTOMER);

            // Create the customer in the data source
            mang.createCustomer(customer);

            // Add the new customer to the local ObservableList and refresh the TableView
            customers.add(customer);
            tvCustomers.refresh();

        } catch (CreateException ex) {
            // Handle the exception by displaying an error alert
            this.showErrorAlert("Unable create the new user");
            LOGGER.severe("Unable create the new user");
        }
    }

    /**
     * Handles the search action for customers based on the provided email.
     *
     * @param event The ActionEvent triggering the method.
     */
    public void handleSearchAction(ActionEvent event) {
        try {
            // Search for customers by email and update the TableView
            customers = FXCollections.observableArrayList(mang.findCustomerByMail(tfMail.getText()));
            tvCustomers.setItems(customers);
            tvCustomers.refresh();
        } catch (ReadException e) {
            // Handle the exception by displaying an error alert
            this.showErrorAlert("Unable to retrieve the users info");
            LOGGER.severe("Unable to retrieve the new user");
        }
    }

    /**
     * Returns an ObservableValue representing the LocalDate of the customer's
     * creation date.
     *
     * @param factory The CellDataFeatures for the TableView.
     * @return The ObservableValue representing the LocalDate of the creation
     * date.
     */
    private ObservableValue<LocalDate> getCustomerToLocalDateCreationDateValueFactory(
            CellDataFeatures<Customer, LocalDate> factory) {
        return new SimpleObjectProperty<>(factory.getValue().getCreationDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Returns a Callback for creating DatePickerTableCell instances for Date
     * columns in the TableView.
     *
     * @return The Callback for creating DatePickerTableCell instances.
     */
    private Callback<TableColumn<Customer, Date>, TableCell<Customer, Date>> getDatePickerCellFactory() {
        return (TableColumn<Customer, Date> param) -> {
            return new DatePickerTableCell();
        };
    }

    /**
     * Handles an action indicating that a feature is still under development.
     *
     * @param event The ActionEvent triggering the method.
     */
    private void handleStillDev(ActionEvent event) {
        this.showErrorAlert("This feature is not supported yet");
    }

    /**
     * Validates a field value against a specified pattern.
     *
     * @param value The value to validate.
     * @param pattern The regular expression pattern for validation.
     * @return True if the value is invalid, false otherwise.
     */
    private boolean validateField(String value, String pattern) {
        return !value.matches(pattern);
    }

    /**
     * Handles the printing action by generating a JasperReport and displaying
     * it using JasperViewer.
     *
     * @param event The ActionEvent triggering the method.
     */
    public void handlePrintOnAction(ActionEvent event) {
        try {
            // Compile the JasperReport from the JRXML file
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/customerReport.jrxml"));

            // Create a JRBeanCollectionDataSource with the customer data
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Customer>) this.tvCustomers.getItems());

            // Set up parameters for the report
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report and create a JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            // Display the report using JasperViewer
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            // Handle the exception by displaying an error alert
            this.showErrorAlert("Error");
        }
    }

}
