/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import entities.Customer;
import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
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

    /**
     * Initializes the controller class.
     * @param root
     */

    public void initStage(Parent root) {
        
        Stage stage = new Stage();
        stage.setTitle("Customer List");
        stage.setResizable(false);
        
       
        
        cbSearchFilters.setItems(FXCollections.observableArrayList("Every Customer", "Customer with trips", "Customers with one week trips"));
        cbSearchFilters.setValue("Every Customer");

        // TableView para mostrar los clientes
        tvCustomers = new TableView<>();
        tvCustomers.setEditable(true);
        
        // TODO: Cargar datos en la tabla usando getAllCustomers()
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        // Llena customers con datos obtenidos de getAllCustomers()
        tvCustomers.setItems(customers);


        // Botones
        btnSearch = new Button("Search");
        btnDelete = new Button("Delete");
        btnShowTrips = new Button("Show Trips");
        btnNewCustomer = new Button("New Customer");
        btnNewCustomer.setDefaultButton(true);


        
        stage.setOnCloseRequest(this::handleOnActionExit);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
