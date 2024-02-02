package view.customer;

import entities.Customer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 * TableCell implementation for displaying and editing Date values using a DatePicker.
 * This class extends TableCell and is specific to the Customer entity.
 * 
 * Usage:
 * In your TableColumn definition, set the cell factory to use DatePickerTableCell:
 * 
 * Example:
 * TableColumn<Customer, Date> dateColumn = new TableColumn<>("Date");
 * dateColumn.setCellFactory(col -> new DatePickerTableCell());
 * 
 * @author Dani
 */
public class DatePickerTableCell extends TableCell<Customer, Date> {

    private DatePicker datePicker;

    /**
     * Default constructor for DatePickerTableCell.
     */
    public DatePickerTableCell() {
    }

    /**
     * Override of the startEdit method to handle starting the editing of the cell.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Override of the cancelEdit method to handle cancelling the editing of the cell.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }

    /**
     * Override of the updateItem method to update the content of the cell.
     * @param item The Date item to be displayed.
     * @param empty Boolean indicating if the cell is empty.
     */
    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                setText(getDate().format(dateFormat));

                setGraphic(null);
            }
        }
    }

    /**
     * Private method to create a DatePicker and configure its properties.
     */
    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        });
    }

    /**
     * Private method to get the LocalDate from the current item in the cell.
     * @return LocalDate representing the current Date item.
     */
    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
