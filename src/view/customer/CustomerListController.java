/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import entities.Customer;
import exception.DeleteException;
import exception.ReadException;
import factories.CustomerManagerFactory;
import interfaces.CustomerManager;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Customer, String> tcCreationDate;

    /**
     * Initializes the controller class.
     * @param root
     */
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
            
           
            btnNewCustomer.setOnAction(this::handleNewAction);
            btnDelete.setOnAction(this::handleDeleteAction);
            stage.setOnCloseRequest(this::handleOnActionExit);
            cbSearchFilters.getSelectionModel().selectedItemProperty().addListener(this::handleOnComboBoxChange);
            tfMail.textProperty().addListener(this::handleTestFielPropertyChange);
            tvCustomers.getSelectionModel().selectedItemProperty().addListener(this::handleSelectRowChange);
            
            stage.setScene(scene);
            stage.show();
        
        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
    
    @FXML
    private void handleOnComboBoxChange(ObservableValue observable, Object oldValue, Object newValue){
        try{  
            
            switch(newValue.toString()){
                case "Every Customer":
                  customers = FXCollections.observableArrayList(mang.findAllCustomers());

                    break;
                case "Customer with trips":
                    customers = FXCollections.observableArrayList(mang.findCustomersWithTrips());
                    break;
            
                case "Customers with one week trips":
                    customers = FXCollections.observableArrayList(mang.findOneWeekTrips());
                    break;
            
            }
        } catch(Exception e){
            this.showErrorAlert(e.getMessage());
        }
    }
    @FXML
    private void handleTestFielPropertyChange(ObservableValue observable, String oldValue, String newValue){
        if (!newValue.isEmpty()) {
            btnSearch.setDisable(false);
        } else {
            btnSearch.setDisable(true);
        }
    }
    
    @FXML
    private void handleSelectRowChange (ObservableValue observable, Object oldValue, Object newValue){
            if (newValue != null) {
            btnDelete.setDisable(false);

        } else {
            btnDelete.setDisable(true);
        }
    }
    @FXML
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
   @FXML
   public void handleNewAction(ActionEvent event) {
        customers.add(new Customer(null, null, null, null, null));
        tvCustomers.refresh();
    }
} 
