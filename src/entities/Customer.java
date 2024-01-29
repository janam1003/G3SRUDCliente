package entities;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a Customer entity, extending the User class and implementing
 * Serializable. This class is mapped to the "customer" table in the "dindb"
 * schema. The primary key is the email address, and it is used as a foreign key
 * in the "tripsInfo" relationship.
 */
@XmlRootElement
public class Customer extends User {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the customer.
     */
    private String name;

    /**
     * The zip code of the customer's address.
     */
    private Integer zip;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The phone number of the customer.
     */
    private Integer phone;

    /**
     * A list of TripInfo objects associated with this customer.
     */
    private List<TripInfo> tripsInfo;

    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
    }

    /**
     * Retrieves the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The new name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the zip code of the customer's address.
     *
     * @return The zip code of the customer's address.
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Sets the zip code of the customer's address.
     *
     * @param zip The new zip code of the customer's address.
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Retrieves the address of the customer.
     *
     * @return The address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The new address of the customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The new phone number of the customer.
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Customer(String name, Integer zip, String address, Integer phone, List<TripInfo> tripsInfo) {
        this.name = name;
        this.zip = zip;
        this.address = address;
        this.phone = phone;
        this.tripsInfo = tripsInfo;
    }
    
    

    /**
     * Retrieves the list of TripInfo objects associated with this customer.
     *
     * @return The list of TripInfo objects associated with this customer.
     */
    @XmlTransient
    public List<TripInfo> getTripsInfo() {
        return tripsInfo;
    }

    /**
     * Sets the list of TripInfo objects associated with this customer.
     *
     * @param tripsInfo The new list of TripInfo objects associated with this
     * customer.
     */
    public void setTripsInfo(List<TripInfo> tripsInfo) {
        this.tripsInfo = tripsInfo;
    }

    /**
     * Computes the hash code for this customer based on the email address.
     *
     * @return The hash code for this customer.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getMail() != null ? this.getMail().hashCode() : 0);
        return hash;
    }

    /**
     * Checks if this customer is equal to another object.
     *
     * @param obj The object to compare with this customer.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Customer other = (Customer) obj;
        return !((this.getMail() == null) ? (other.getMail() != null) : !this.getMail().equals(other.getMail()));
    }

    @Override
    public String toString() {
        return super.toString() + "Customer{" + "name=" + name + ", zip=" + zip + ", address=" + address + ", phone=" + phone + ", tripsInfo=" + tripsInfo + '}';
    }

    /**
     * Generates a string representation of this customer.
     *
     * @return A string representation of this customer.
     */
}
