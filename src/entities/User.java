package entities;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity JPA class for user data. The properties of this class are login ,
 * name, profile and department.
 *
 * @author javi
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id field for user entity. It is also the mail id value for the user.
     */

    private String mail;
    /**
     * Password of the user.
     */
    private String password;
    /**
     * The date of the User creation.
     */
    private Date creationDate;
    /**
     * {@link EnumUserType} value for the user.
     */
    private EnumUserType userType;

    public User(String mail, String password, Date creationDate, EnumUserType userType) {
        this.mail = mail;
        this.password = password;
        this.creationDate = creationDate;
        this.userType = userType;
    }

    public User() {
    }

    /**
     * Gets mail value for user.
     *
     * @return The mail value.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets mail value for user.
     *
     * @param mail The login value.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets name value for user.
     *
     * @return The name value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password value for user.
     *
     * @param password The name value.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets creationDate value for user.
     *
     * @return The creationDate value.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creationDate value for user.
     *
     * @param creationDate The login value.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets Profile value for user.
     *
     * @return The Profile value.
     */
    public EnumUserType getUserType() {
        return userType;
    }

    /**
     * Sets Profile value for user.
     *
     * @param userType The Profile value.
     */
    public void setUserType(EnumUserType userType) {
        this.userType = userType;
    }

    /**
     * HashCode method implementation for the entity.
     *
     * @return An integer value as hashcode for the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mail != null ? mail.hashCode() : 0);
        return hash;
    }

    /**
     * This method compares two user entities for equality. This implementation
     * compare login field value for equality.
     *
     * @param object The object to compare to.
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.mail == null && other.mail != null)
                || (this.mail != null && !this.mail.equals(other.mail))) {
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation for a user entity instance.
     *
     * @return The String representation for the user object.
     */
    @Override
    public String toString() {
        return "User{" + "mail=" + mail + ", password=" + password + ", userType=" + userType + ", creationDate=" + creationDate + '}';
    }

}
