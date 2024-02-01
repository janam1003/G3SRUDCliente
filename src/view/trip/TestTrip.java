package view.trip;

import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.TableViewMatchers;

import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

@RunWith(org.testfx.runner.JUnitCore.class)
public class TripControllerTest extends ApplicationTest {

    private TableView<Trip> tableViewTrips;
    private DatePicker datePickerStart, datePickerEnd;
    private Button btSearch, btPurchaseCancel;
    private ComboBox<String> cbSearchOptions, cbTripType;
    private MenuItem menuItemBook, menuItemCancel;
    private RadioButton rbActive, rbInactive, rbBoth;

    @Before
    public void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TripController.class);
    }

    @Override
    public void start(Stage stage) {
        // Initialize your JavaFX application here
    }

    // Tests expected to pass

    @Test
    public void testSearchAndBookAvailableTrips() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        select(cbSearchOptions, "Available Trips");

        // Fill the two dates
        datePickerStart.setValue(LocalDate.now());
        datePickerEnd.setValue(LocalDate.now().plusDays(10));

        // Click on search
        clickOn(btSearch);

        // Assert that the TableView has items
        verifyThat(tableViewTrips, TableViewMatchers.hasItems(1));

        // Click on book
        clickOn(menuItemBook);
        // Additional logic to handle booking
    }

    @Test
    public void testDeleteMyTrip() {
        // Select "My Trips" in the ComboBox
        clickOn(cbSearchOptions);
        select(cbSearchOptions, "My Trips");

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

    // Add 5 more tests that make sense for the application

    // Tests expected to fail

    @Test(expected = AssertionError.class)
    public void testSearchWithoutFillingDates() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        select(cbSearchOptions, "Available Trips");

        // Click on search without filling the dates
        clickOn(btSearch);

        // Assert that the TableView is empty
        verifyThat(tableViewTrips, TableViewMatchers.isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void testSearchWithEndDateBeforeStartDate() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        select(cbSearchOptions, "Available Trips");

        // Fill the dates with end date being sooner than start date
        datePickerStart.setValue(LocalDate.now().plusDays(10));
        datePickerEnd.setValue(LocalDate.now());

        // Click on search
        clickOn(btSearch);

        // Assert that the TableView is empty
        verifyThat(tableViewTrips, TableViewMatchers.isEmpty());
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

    // Add 5 more tests that make sense for the application and are expected to fail

    private void select(ComboBox<String> comboBox, String value) {
        // Implement selection logic for ComboBox
    }


	///////////////////////////////  2 ntirada gpt  ///////////////////////////////
	@Test
    public void testSearchAvailableTrips() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        selectComboBoxItem(cbSearchOptions, "Available Trips");

        // Fill the two dates
        datePickerStart.setValue(LocalDate.now());
        datePickerEnd.setValue(LocalDate.now().plusDays(10));

        // Click on search
        clickOn(btSearch);

        // Assert that the TableView is not empty
        FxAssert.verifyThat(tableViewTrips, NodeMatchers.isNotNull());
    }

    @Test
    public void testSearchWithoutFillingDates() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        selectComboBoxItem(cbSearchOptions, "Available Trips");

        // Click on search without filling the dates
        clickOn(btSearch);

        // Assert that the TableView is empty
        FxAssert.verifyThat(tableViewTrips, NodeMatchers.isNull());
    }

    @Test
    public void testSearchWithEndDateBeforeStartDate() {
        // Select "Available Trips" in the ComboBox
        clickOn(cbSearchOptions);
        selectComboBoxItem(cbSearchOptions, "Available Trips");

        // Fill the dates with end date being sooner than start date
        datePickerStart.setValue(LocalDate.now().plusDays(10));
        datePickerEnd.setValue(LocalDate.now());

        // Click on search
        clickOn(btSearch);

        // Assert that the TableView is empty
        FxAssert.verifyThat(tableViewTrips, NodeMatchers.isNull());
    }

    // Add similar tests for "My Trips", "Delete", "Change Date", and other functionalities
	@Override
    public void start(Stage stage) throws Exception {
        // Initialize your JavaFX application class here
        // For example: new YourApplicationClass().start(stage);
        
        // Lookup for nodes to be used in testing
        tableViewTrips = lookup("#tableViewTrips").queryTableView();
        btSearch = lookup("#btSearch").queryButton();
        btPurchaseCancel = lookup("#btPurchaseCancel").queryButton();
        cbSearchOptions = lookup("#cbSearchOptions").queryComboBox();
        dpStartDate = lookup("#dpStartDate").queryDatePicker();
        dpEndDate = lookup("#dpEndDate").queryDatePicker();
    }

    @Test
    public void testSearchAvailableTrips() {
        // Select "Available Trips" from the combo box
        clickOn(cbSearchOptions);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        // Fill in the start and end dates
        clickOn(dpStartDate).write("01/01/2024");
        clickOn(dpEndDate).write("01/31/2024");
        
        // Click on search
        clickOn(btSearch);
        
        // Assert that the table has items
        assertFalse(tableViewTrips.getItems().isEmpty());
    }

    @Test
    public void testBookTrip() {
        // Assume that a trip is selected and dates are filled
        // This should be set up accordingly if your application requires it
        
        // Click on book
        clickOn(btPurchaseCancel);
        
        // Assert that the trip is booked
        // This assertion will depend on how booking is reflected in your application
    }

    @Test
    public void testDeleteTrip() {
        // Select "My Trips" from the combo box
        clickOn(cbSearchOptions);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        // Select the first row in the table
        clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));
        
        // Click on cancel/delete
        clickOn(btPurchaseCancel);
        
        // Assert that the trip is deleted
        // This assertion will depend on how deletion is reflected in your application
    }
	//////// FAILED///////
	@Test(expected = Exception.class)
    public void testSearchWithoutDates() {
        // Select "Available Trips" from the combo box
        clickOn(cbSearchOptions);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        // Do not fill in the start and end dates
        
        // Click on search
        clickOn(btSearch);
        
        // Expect an exception due to missing dates
    }

    @Test(expected = Exception.class)
    public void testEndDateBeforeStartDate() {
        // Select "Available Trips" from the combo box
        clickOn(cbSearchOptions);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        // Fill in the start and end dates with the end date before the start date
        clickOn(dpStartDate).write("01/31/2024");
        clickOn(dpEndDate).write("01/01/2024");
        
        // Click on search
        clickOn(btSearch);
        
        // Expect an exception due to invalid date range
    }

    @Test(expected = Exception.class)
    public void testChangeToDateLaterThanEndDate() {
        // Select "My Trips" from the combo box
        clickOn(cbSearchOptions);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        // Select the first row in the table
        clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));
        
        // Attempt to change the start date to a date later than the end date
        // This will depend on how your application allows editing dates
        
        // Expect an exception due to invalid date range
    }

    // Add more tests for other scenarios that should fail...
}


