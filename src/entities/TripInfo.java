package entities;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for TripInfo, which is the attribufe from the nm relation between Trip
 * and Customer
 *
 * @author Iñigo
 */
@XmlRootElement
public class TripInfo implements Serializable {

    TripInfoId tripInfoId;

    Trip trip;

    Customer customer;

    Date initialDate;

    Date lastDate;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tripInfoId == null) ? 0 : tripInfoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TripInfo other = (TripInfo) obj;
        if (tripInfoId == null) {
            if (other.tripInfoId != null) {
                return false;
            }
        } else if (!tripInfoId.equals(other.tripInfoId)) {
            return false;
        }
        return true;
    }
    //@XmlTransient

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @XmlTransient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TripInfoId getTripInfoId() {
        return tripInfoId;
    }

    public void setTripInfoId(TripInfoId tripInfoId) {
        this.tripInfoId = tripInfoId;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "TripInfo [tripInfoId=" + tripInfoId + ", trip=" + trip + ", customer=" + customer + ", initialDate="
                + initialDate + ", lastDate=" + lastDate + "]";
    }

}
