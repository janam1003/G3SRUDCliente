package view.trip;

import static org.testfx.api.FxAssert.verifyThat;

import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;

import entities.Customer;
import entities.Trip;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class TripControllerTest extends ApplicationTest {

	private TableView<Trip> tableViewTrips;
	private DatePicker datePickerStart, datePickerEnd;
	private Button btSearch, btPurchaseCancel;
	private ComboBox<String> cbSearchOptions, cbTripType;
	private MenuItem menuItemBook, menuItemCancel;
	private RadioButton rbActive, rbInactive, rbBoth;

	@Override
	public void start(Stage stage) {
		FXMLLoader loaderTrip = new FXMLLoader(getClass().getResource("/view/trip/Trip.fxml"));
		Parent root = (Parent) loaderTrip.load();

		// Get the controller
		TripController controller = (TripController) loaderTrip.getController();

		// Set the stage
		controller.setStage(stage);

		// create the object of the logged customer
		Customer customer = new Customer();

		customer.setMail("customer@gmail.com");

		// Initialize the stage
		controller.initStage(root, customer);

		// Get the UI components
		tableViewTrips = lookup("#tableViewTrips").query();
		datePickerStart = lookup("#datePickerStart").query();
		datePickerEnd = lookup("#datePickerEnd").query();
		btSearch = lookup("#btSearch").query();
		btPurchaseCancel = lookup("#btPurchaseCancel").query();
		cbSearchOptions = lookup("#cbSearchOptions").query();
		cbTripType = lookup("#cbTripType").query();
		rbActive = lookup("#rbActive").query();
		rbInactive = lookup("#rbInactive").query();
		rbBoth = lookup("#rbBoth").query();
	}

	// Tests expected to pass

	@Test
	public void testSearchAndBookAvailableTrips() {
		// "Available Trips" is selected by default in the ComboBox
		// Triptype empty by default

		// Click on search
		clickOn(btSearch);
		// Fill the two dates
		datePickerStart.setValue(LocalDate.now());
		datePickerEnd.setValue(LocalDate.now().plusDays(10));
		// Select the first row in the TableView
		//clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));
		// Click on book
		clickOn(btPurchaseCancel);
		// Select on search options combo second option
		clickOn(cbSearchOptions);
		select(cbSearchOptions, "My Trips");
		// Click on search
		clickOn(btSearch);
		// Get table data and check if there is a row with that dates
		tableViewTrips.getItems().stream().filter(trip -> trip.tripInfo.get(0).getStartDate().equals(LocalDate.now()))
				.filter(trip -> trip.getEndDate().equals(LocalDate.now().plusDays(10))).findFirst().get();
	}

	@Test
	public void testDeleteMyTrip() {
		// Select "My Trips" in the ComboBox
		clickOn(cbSearchOptions);
		select(cbSearchOptions, "My Trips");
		// Select search button
		clickOn(btSearch);
		// Select the first row in the TableView
		clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));
		// Click on delete
		clickOn(menuItemCancel);
		// Additional logic to handle deletion
	}

	@Test
	public void testChangeDateMyTrip() {
		// Select "My Trips" in the ComboBox
		clickOn(cbSearchOptions);
		select(cbSearchOptions, "My Trips");

		// Select the first row in the TableView
		clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));

		// Change the date
		datePickerStart.setValue(LocalDate.now().plusDays(1));
		datePickerEnd.setValue(LocalDate.now().plusDays(11));

		// Additional logic to handle date change
	}


	@Test(expected = AssertionError.class)
	public void testChangeDateWithEndDateBeforeStartDateMyTrip() {
		// Select "My Trips" in the ComboBox
		clickOn(cbSearchOptions);
		select(cbSearchOptions, "My Trips");

		// Select the first row in the TableView
		clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));

		// Change the date with end date before start date
		datePickerStart.setValue(LocalDate.now().plusDays(10));
		datePickerEnd.setValue(LocalDate.now());

		// Additional logic to handle date change
	}
}
