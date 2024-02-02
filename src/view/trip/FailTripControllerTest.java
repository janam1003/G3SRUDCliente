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
/*
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

    @Test(expected = AssertionError.class)
    public void testBookWithoutFillingDates() {
        // Click on search without filling the dates
        clickOn(btSearch);
		//Select a table row
		clickOn(tableViewTrips.lookup(".table-row-cell").nth(0));
		//Click on book
		clickOn(btPurchaseCancel);
		// Assert error message alert
		verifyThat("Error", NodeMatchers.isNotNull());
    }

    @Test(expected = AssertionError.class)
    public void testBookWithEndDateBeforeStartDate() {
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

}


*/