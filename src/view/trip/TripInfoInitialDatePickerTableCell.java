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



public class TripInfoInitialDatePickerTableCell extends TableCell<Trip, TripInfo>{
    private DatePicker datePicker;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public TripInfoInitialDatePickerTableCell() {

    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

       // setText(getDate().format(dateFormat));
        setGraphic(null);
    }

   
    @Override
    public void updateItem(TripInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                   // datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
			
                setGraphic(null);

            }
        }
    }

    private void createDatePicker() {
		datePicker = new DatePicker();
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
			//obtein the object trip that is in the row
			Trip trip = (Trip) getTableRow().getItem();
			//obtein the tripInfo of the trip
			TripInfo copy = trip.getTripInfo().get(0);

            if(datePicker.getValue() != null){
                copy.setInitialDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                trip.getTripInfo().set(0, copy);
				commitEdit(copy);
            }else{
                cancelEdit();
            }
        });
    }

	// private LocalDate getDate() {
	// 	return getItem() == null ? null : getItem().getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	// }
}