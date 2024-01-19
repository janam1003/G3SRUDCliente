/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import entities.Customer;
import exception.ReadException;
import factories.CustomerManagerFactory;
import interfaces.CustomerManager;
import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.generic.GenericController;

/**
 * FXML Controller class
 *
 * @author danid
 */
public class CustomerListController extends GenericController {
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

    public void initStage(Parent root) {
        
        try {
            CustomerManager mang = CustomerManagerFactory.getCustomerManager();
            
            Scene scene = new Scene(root);
            
            stage.setTitle("Customer List");
            stage.setResizable(false);
            
            this.btnNewCustomer.setDefaultButton(true);
            this.btnDelete.setDisable(true);
            this.btnShowTrips.setDisable(true);
            this.btnSearch.setDisable(true);
            
            
            
            
            cbSearchFilters.setItems(FXCollections.observableArrayList("Every Customer", "Customer with trips", "Customers with one week trips"));
            cbSearchFilters.setValue("Every Customer");
            
            // TableView para mostrar los clientes
            tvCustomers.setEditable(true);
 
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

            tcMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

            tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

            tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

            tcZip.setCellValueFactory(new PropertyValueFactory<>("zip"));

            tcCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            
            // TODO: Cargar datos en la tabla usando getAllCustomers()
            customers = FXCollections.observableArrayList(mang.findAllCustomers());
            // Llena customers con datos obtenidos de getAllCustomers()
            tvCustomers.setItems(customers);
            
            
            
            stage.setOnCloseRequest(this::handleOnActionExit);
            
            stage.setScene(scene);
            stage.show();
        } catch (ReadException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
}
