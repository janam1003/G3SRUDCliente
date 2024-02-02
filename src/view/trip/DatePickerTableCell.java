package view.trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import entities.Trip;
import entities.TripInfo;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
/**
 * Class that extends TableCell to create a DatePicker in a TableView
 * @author Inigo
 */
public class DatePickerTableCell extends TableCell<Trip, LocalDate> {
	/**
	 * DatePicker to be created
	 */
    private DatePicker datePicker;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
	 * String to save the old value
	 */
	private static String oldValue;

    public DatePickerTableCell() {

    }
	/**
	 * Method to start the edit
	 */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            oldValue = getText();
            setGraphic(datePicker);
        }
    }
	/**
	 * Method called when cancel the edit
	 */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        createDatePicker();
        setGraphic(null);
        setText(oldValue);
    }
	/**
	 * Method to update the item
	 * @param item LocalDate to be updated
	 * @param empty boolean to check if the item is empty
	 */
    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(item);
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                if (item != null) {
                    setText(item.format(dateFormat));
                }
                setGraphic(null);

            }
        }
    }
	/**
	 * Method to create a DatePicker
	 */
    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(datePicker.getValue());
        });

    }
}
