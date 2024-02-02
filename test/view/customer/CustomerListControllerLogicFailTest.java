/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customer;

import de.jensd.fx.glyphs.testapps.App;
import entities.Customer;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;

/**
 *
 * @author danid
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerListControllerLogicFailTest extends ApplicationTest  {

    
    private static int number;

    private TableView<Customer> tvCustomers;

    private void launch(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/customer/CustomerList.fxml"));
            Parent root = (Parent) loader.load();
            CustomerListController controller = CustomerListController.class
                    .cast(loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        new CustomerListControllerLogicFailTest().launch(stage);
        tvCustomers = lookup("#tvCustomers").queryTableView();
    }

    @Override
    public void stop() {
    }

    @Test
    public void Test_A_EmailAlreadyInUse() {
        
        number = new Random().nextInt(999999999);
        String mail = "Test" + number + "@gmail.com";
        
        clickOn("#btnNewCustomer");
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Integer tablerow = tvCustomers.getSelectionModel().getSelectedIndex();

        Node tableColumnName = lookup("#tcName").nth(tablerow + 1).query();
        clickOn(tableColumnName).write("Paco");
        push(KeyCode.ENTER);

        tableColumnName = lookup("#tcMail").nth(tablerow + 1).query();
        clickOn(tableColumnName).write(mail);
        push(KeyCode.ENTER);

        tableColumnName = lookup("#tcPhone").nth(tablerow + 1).query();
        clickOn(tableColumnName).write("688888888");
        push(KeyCode.ENTER);
        
        tableColumnName = lookup("#tcAddress").nth(tablerow + 1).query();
        clickOn(tableColumnName).write("Example Address");
        push(KeyCode.ENTER);
        
        tableColumnName = lookup("#tcZip").nth(tablerow + 1).query();
        clickOn(tableColumnName).write("40000");
        push(KeyCode.ENTER);
        
        tableColumnName = lookup("#tcCreationDate").nth(tablerow + 1).query();
        doubleClickOn(tableColumnName).clickOn(tableColumnName).eraseText(1).write("15/02/2003");
        push(KeyCode.ENTER);
        
        clickOn("#btnNewCustomer");
        tvCustomers.getSelectionModel().select(tvCustomers.getItems().stream().filter(c -> !c.getMail().equals(mail)).collect(Collectors.toList()).get(0));   
        tableColumnName = lookup("#tcMail").nth(tvCustomers.getSelectionModel().getSelectedIndex()).query();
        doubleClickOn(tableColumnName).clickOn(tableColumnName).eraseText(1).write(mail);
        push(KeyCode.ENTER);
        verifyThat("This mail is already in use", isVisible());
        push(KeyCode.ENTER);

    }
    @Test
    public void Test_B_WrongCreationDate() {
        
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Integer tablerow = tvCustomers.getSelectionModel().getSelectedIndex();
        Node tableColumnName = lookup("#tcCreationDate").nth(tablerow + 1).query();
        doubleClickOn(tableColumnName).clickOn(tableColumnName).eraseText(1).write("15/02/3000");
        push(KeyCode.ENTER);
        verifyThat("Date must be before the actual date", isVisible());
    
    }
}
