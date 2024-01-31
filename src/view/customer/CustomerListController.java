/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import entities.Customer;
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
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public void initStage(Parent root) {
        try {

            Scene scene = new Scene(root);

            stage.setTitle("Customer List");
            stage.setResizable(false);

            btnNewCustomer.setDefaultButton(true);
            btnDelete.setDisable(true);
            btnShowTrips.setDisable(true);
            btnSearch.setDisable(true);
            cmmiDelete.setDisable(true);

            cbSearchFilters.setItems(FXCollections.observableArrayList("Every Customer", "Customer with trips", "Customers with one week trips"));
            cbSearchFilters.setValue("Every Customer");

            tvCustomers.setEditable(true);
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            tcZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
            tcCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

            // Cargar datos en la tabla usando getAllCustomers()
            customers = FXCollections.observableArrayList(mang.findAllCustomers());
            // Llena customers con datos obtenidos de getAllCustomers()
            tvCustomers.setItems(customers);
            tvCustomers.setEditable(true);

            tcName.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("Name"));
            tcName.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Name cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Name cannot be empty");
                    } else if (validateField(t.getNewValue(), namePattern)) {
                        throw new CredentialException("Make sure that the Name is well written");
                    } else {
                        customer.setName(t.getNewValue());
                    }
                     mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert("Unable to modify the name");
                } catch (CredentialException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });

            tcMail.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcMail.setCellValueFactory(
                    new PropertyValueFactory<>("Mail"));
            tcMail.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Mail cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Mail cannot be empty");
                    } else if (validateField(t.getNewValue(), mailPattern)) {
                        throw new CredentialException("Make sure that the mail is well written");
                    } else {
                        customer.setMail(t.getNewValue());
                    }
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setMail(t.getOldValue());
                    this.showErrorAlert("Unable to modify the mail");
                } catch (CredentialException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });

            tcAddress.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcAddress.setCellValueFactory(
                    new PropertyValueFactory<>("Address"));
            tcAddress.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Mail cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Mail cannot be empty");
                    } else {
                        customer.setAddress(t.getNewValue());
                    }
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setAddress(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Address");
                } catch (CredentialException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });

            tcZip.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcZip.setCellValueFactory(
                    new PropertyValueFactory<>("Zip"));
            tcZip.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Zip cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Zip cannot be empty");
                    } else if (validateField(t.getNewValue(), zipPattern)) {
                        throw new CredentialException("Make sure that the zip is well written");
                    } else {
                        customer.setZip(t.getNewValue());
                    }
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setMail(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Zip");
                } catch (CredentialException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });
            tcPhone.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
            tcPhone.setCellValueFactory(
                    new PropertyValueFactory<>("Phone"));
            tcPhone.setOnEditCommit((TableColumn.CellEditEvent<Customer, String> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().length() > 254) {
                        throw new CredentialException("Phone cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Phone cannot be empty");
                    } else if (validateField(t.getNewValue(), phonePattern)) {
                        throw new CredentialException("Make sure that the phone is well written");
                    } else {
                        customer.setPhone(t.getNewValue());
                    }
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setPhone(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Phone");
                } catch (CredentialException e) {
                    customer.setName(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });
            Callback<TableColumn<Customer, Date>, TableCell<Customer, Date>> dateCellFactory;
            tcCreationDate.setCellFactory(getDatePickerCellFactory());
            tcCreationDate.setCellValueFactory(
                    new PropertyValueFactory<>("creationDate"));
            tcCreationDate.setOnEditCommit((TableColumn.CellEditEvent<Customer, Date> t) -> {
                Customer customer = ((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow()));
                try {
                    if (t.getNewValue().toString().length() > 254) {
                        throw new CredentialException("Phone cannot exceed 254 characters");
                    } else if (t.getNewValue().equals("")) {
                        throw new CredentialException("Date cannot be empty");
                    } else {
                        customer.setCreationDate(t.getNewValue());
                    }
                    mang.updateCustomer(customer, true);

                } catch (UpdateException e) {
                    customer.setCreationDate(t.getOldValue());
                    this.showErrorAlert("Unable to modify the Phone");
                } catch (CredentialException e) {
                    customer.setCreationDate(t.getOldValue());
                    this.showErrorAlert(e.getMessage());
                }
            });

            btnNewCustomer.setOnAction(this::handleNewAction);
            cmmiAdd.setOnAction(this::handleNewAction);
            btnDelete.setOnAction(this::handleDeleteAction);
            cmmiDelete.setOnAction(this::handleDeleteAction);
            btnSearch.setOnAction(this::handleSearchAction);
            stage.setOnCloseRequest(this::handleOnActionExit);
            cbSearchFilters.getSelectionModel().selectedItemProperty().addListener(this::handleOnComboBoxChange);
            tfMail.textProperty().addListener(this::handleTestFielPropertyChange);
            tvCustomers.getSelectionModel().selectedItemProperty().addListener(this::handleSelectRowChange);
            
            //Not developed additions
            btnPrintDocument.setOnAction(this::handleStillDev);
            btnShowTrips.setOnAction(this::handleStillDev);
            menuHelp.setOnAction(this::handleStillDev);
            menuCustomers.setOnAction(this::handleStillDev);
            menuExit.setOnAction(this::handleStillDev);
            menuTrips.setOnAction(this::handleStillDev);
            cmmiHelp.setOnAction(this::handleStillDev);
            cmmiPrint.setOnAction(this::handleStillDev);
            

            
            stage.setScene(scene);
            stage.show();

        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
 * Handles the change in selection of the ComboBox by updating the displayed customers
 * based on the selected option.
 *
 * @param observable The observable value being watched.
 * @param oldValue   The old value before the change.
 * @param newValue   The new value after the change.
 */
private void handleOnComboBoxChange(ObservableValue observable, Object oldValue, Object newValue) {
    try {
        switch (newValue.toString()) {
            case "Every Customer":
                customers = FXCollections.observableArrayList(mang.findAllCustomers());
                tvCustomers.setItems(customers);
                tvCustomers.refresh();
                break;
            case "Customer with trips":
                customers = FXCollections.observableArrayList(mang.findCustomersWithTrips());
                tvCustomers.setItems(customers);
                tvCustomers.refresh();
                break;
            case "Customers with one week trips":
                customers = FXCollections.observableArrayList(mang.findOneWeekTrips());
                tvCustomers.setItems(customers);
                tvCustomers.refresh();
                break;
        }
    } catch (ReadException e) {
        this.showErrorAlert("Error getting the stored customers");
    }
}

/**
 * Handles the change in property of the TextField and enables/disables the search button accordingly.
 *
 * @param observable The observable value being watched.
 * @param oldValue   The old value before the change.
 * @param newValue   The new value after the change.
 */
private void handleTestFielPropertyChange(ObservableValue observable, String oldValue, String newValue) {
    if (!newValue.isEmpty()) {
        btnSearch.setDisable(false);
    } else {
        btnSearch.setDisable(true);
    }
}

/**
 * Handles the change in selection of a row in the table and updates the state of various buttons accordingly.
 *
 * @param observable The observable value being watched.
 * @param oldValue   The old value before the change.
 * @param newValue   The new value after the change.
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
 * Handles the action of deleting a customer.
 *
 * @param event The action event triggering the method.
 */
public void handleDeleteAction(ActionEvent event) {
    try {
        Customer userToDelete = (Customer) tvCustomers.getSelectionModel().getSelectedItem();
        mang.deleteCustomer(userToDelete.getMail());
        customers.remove(userToDelete);
        tvCustomers.getSelectionModel().clearSelection();
        tvCustomers.refresh();
    } catch (DeleteException e) {
        this.showErrorAlert("Unable to Delete the selected User");
    }
}

/**
 * Handles the action of creating a new customer.
 *
 * @param event The action event triggering the method.
 */
public void handleNewAction(ActionEvent event) {
    try {
        Customer customer = new Customer();
        // Initialization of customer properties
        // ...

        mang.createCustomer(customer);
        customers.add(customer);
        tvCustomers.refresh();
    } catch (CreateException ex) {
        this.showErrorAlert("Unable create the new user");
    }
}

/**
 * Handles the action of searching for a customer based on the entered mail in the TextField.
 *
 * @param event The action event triggering the method.
 */
public void handleSearchAction(ActionEvent event) {
    try {
        customers = FXCollections.observableArrayList(mang.findCustomerByMail(tfMail.getText()));
        tvCustomers.setItems(customers);
        tvCustomers.refresh();
    } catch (ReadException e) {
        this.showErrorAlert("Unable to retrieve the users info");
    }
}

/**
 * Provides a custom value factory for converting Customer's creation date to a LocalDate.
 *
 * @param factory The cell data features for the table column.
 * @return An observable value representing the LocalDate of the customer's creation date.
 */
private ObservableValue<LocalDate> getCustomerToLocalDateCreationDateValueFactory(
        CellDataFeatures<Customer, LocalDate> factory) {
    return new SimpleObjectProperty<>(factory.getValue().getCreationDate()
            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
}

/**
 * Provides a callback for creating a DatePickerTableCell for a Date column in the table.
 *
 * @return A callback for creating DatePickerTableCells for Date columns.
 */
private Callback<TableColumn<Customer, Date>, TableCell<Customer, Date>> getDatePickerCellFactory() {
    return (TableColumn<Customer, Date> param) -> {
        return new DatePickerTableCell();
    };
}

/**
 * Handles the action of a feature that is still in development, displaying an alert.
 *
 * @param event The action event triggering the method.
 */
private void handleStillDev(ActionEvent event) {
    this.showErrorAlert("This feature is not supported yet");
}

/**
 * Validates a given field value against a specified pattern.
 *
 * @param value   The value to be validated.
 * @param pattern The regular expression pattern for validation.
 * @return True if the value is valid according to the pattern, false otherwise.
 */
private boolean validateField(String value, String pattern) {
    return !value.matches(pattern);
}


}
