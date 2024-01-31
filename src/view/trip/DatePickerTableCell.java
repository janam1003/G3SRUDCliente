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

public class DatePickerTableCell extends TableCell<Trip, LocalDate> {

    private DatePicker datePicker;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static String oldValue;

    public DatePickerTableCell() {

    }

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

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        createDatePicker();
        setGraphic(null);
        setText(oldValue);
    }

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

    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(datePicker.getValue());
        });

    }
}
