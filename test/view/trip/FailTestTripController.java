package view.trip;

import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;

import entities.Customer;
import static entities.EnumTripType.CULTURE;
import entities.Trip;
import java.text.ParseException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class FailTestTripController extends ApplicationTest {

	private TableView<Trip> tableViewTrips;
	private DatePicker datePickerStart, datePickerEnd;
	private Button btSearch, btPurchaseCancel;
	private ComboBox<String> cbSearchOptions, cbTripType;
	private MenuItem menuItemBook, menuItemCancel;
	private RadioButton rbActive, rbInactive, rbBoth;

	private void launch(Stage stage) throws Exception {
		FXMLLoader loaderTrip = new FXMLLoader(getClass().getResource("/view/trip/Trip.fxml"));
		Parent root = (Parent) loaderTrip.load();
		TripController controller = (TripController) loaderTrip.getController();
		controller.setStage(stage);
		// create the object of the logged customer
		Customer customer = new Customer();
		customer.setMail("customer@gmail.com");
		controller.initStage(root, customer);
	}

	@Override
	public void start(Stage stage) throws Exception {
		launch(stage);
		// Get the UI components
		tableViewTrips = lookup("#tableViewTrips").query();

		btSearch = lookup("#btSearch").query();
		btPurchaseCancel = lookup("#btPurchaseCancel").query();
		cbSearchOptions = lookup("#cbSearchOptions").query();
		cbTripType = lookup("#cbTripType").query();
		rbActive = lookup("#rbActive").query();
		rbInactive = lookup("#rbInactive").query();
		rbBoth = lookup("#rbBoth").query();
	}

	@Test
	public void FailSearchMyTrips() {
		// seleccionando "My Trips" en el ComboBox de opciones de búsqueda
		doubleClickOn(cbSearchOptions);
		clickOn();
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		doubleClickOn(rbInactive);
		// Click on search
		clickOn(btSearch);
		verifyThat(".dialog-pane .header-panel .label", hasText("Error"));
	}

	@Test
	public void cFailDateBookTrips() throws ParseException {
		// "Available Trips" is selected by default in the ComboBox
		// Triptype empty by default
		// Click on search
		clickOn(btSearch);
		sleep(1000);
		Trip trip = tableViewTrips.getItems().get(0);
		sleep(2000);
		Node row = lookup(".table-row-cell").nth(0).query();
		clickOn(row);
		// Click on book
		clickOn(btPurchaseCancel);
		sleep(2000);
		// Check error select date
		verifyThat(".dialog-pane .header-panel .label", hasText("Error"));
		push(KeyCode.ENTER);
		clickOn(row);
		Node tableColumnName = lookup("#tableColumnStart").nth(0 + 1).query();
		doubleClickOn(tableColumnName);
		clickOn();
		write("02/02/2024");
		push(KeyCode.ENTER);
		Node tableColumnName2 = lookup("#tableColumnEnd").nth(0 + 1).query();
		doubleClickOn(tableColumnName2);
		clickOn();
		write("02/01/2024");
		push(KeyCode.ENTER);
		// Click on book
		clickOn(btPurchaseCancel);
		sleep(2000);
		// Checkerror
		verifyThat(".dialog-pane .header-panel .label", hasText("Error"));
	}

	@Test
	public void dFailModifyMyTrip() throws ParseException {
		// seleccionando "My Trips" en el ComboBox de opciones de búsqueda
		doubleClickOn(cbSearchOptions);
		clickOn();
		push(KeyCode.DOWN);
		push(KeyCode.ENTER);
		// Click on search
		clickOn(btSearch);
		sleep(2000);
		Node row = lookup(".table-row-cell").nth(0).query();
		clickOn(row);
		Node tableColumnName = lookup("#tableColumnStart").nth(0 + 1).query();
		doubleClickOn(tableColumnName);
		clickOn();
		write("04/02/2025");
		push(KeyCode.ENTER);
		clickOn(btSearch);
		sleep(2000);
		verifyThat(".dialog-pane .header-panel .label", hasText("Error"));
	}
}
