package view.trip;

import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import entities.Trip;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


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
}
}
