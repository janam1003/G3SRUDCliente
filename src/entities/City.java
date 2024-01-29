package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * It is an City entitywhich is Serializable. Containing attributes CityId,
 * name, country,populationType and weatherType.
 *
 * @author Janam
 */
@XmlRootElement
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identification field for the City and is AutoGenetrated.
     */
    private Long cityId;

    /**
     * Name of the City.
     */
    private String name;

    /**
     * Name of country of that City.
     */
    private String country;

    /**
     * populationType of the City.
     */
    private PopulationType populationType;

    /**
     * weatherType of the City.
     */
    private WeatherType weatherType;

    /**
     * Relational field for cities trips.
     */
    private List<Trip> trips;

    /**
     * Empty Constructor.
     */
    public City() {

    }

    /**
     * @return the CityId of City
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param CityId City CityId.
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Get the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the populationType
     *
     * @return populationType
     */
    public PopulationType getPopulationType() {
        return populationType;
    }

    /**
     * @param populationType the populationType to set
     */
    public void setPopulationType(PopulationType populationType) {
        this.populationType = populationType;
    }

    /**
     * Get the weatherType
     *
     * @return weatherType
     */
    public WeatherType getWeatherType() {
        return weatherType;
    }

    /**
     * @param weatherType the weatherType to set
     */
    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    /**
     * Get the trips
     *
     * @return trips
     */
    public List<Trip> getTrip() {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrip(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Integer representation for City instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cityId);
        return hash;
    }

    /**
     * Compares two City objects for equality. This method consider a City equal
     * to another City if their id fields have the same value.
     *
     * @param object The other City object to compare to.
     * @return true if CityIds are equals.
     */
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
        final City other = (City) obj;
        return Objects.equals(this.cityId, other.cityId);
    }

    /**
     * Obtains a string representation of the City.
     *
     * @return The String representing the City.
     */
    @Override
    public String toString() {
        return "City{" + "cityId=" + cityId + ", name=" + name + ", country=" + country + ", populationType=" + populationType + ", weatherType=" + weatherType + ", trip=" + trips + '}';
    }
}
