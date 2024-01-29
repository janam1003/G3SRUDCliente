package entities;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Entity for Trip, it has id, cities, tripType and description
 *
 * @author Iñigo
 */

public class Trip implements Serializable {

    private Integer id;

    private List<TripInfo> tripInfo;

    private List<City> cities;

    private EnumTripType tripType;

    private String description;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Trip other = (Trip) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<TripInfo> getTripInfo() {
        return tripInfo;
    }

    public void setTripInfo(List<TripInfo> tripInfo) {
        this.tripInfo = tripInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnumTripType getTripType() {
        return tripType;
    }

    public void setTripType(EnumTripType tripType) {
        this.tripType = tripType;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + ", cities=" + cities + ", tripType=" + tripType + ", description=" + description
                + "]";
    }

}
