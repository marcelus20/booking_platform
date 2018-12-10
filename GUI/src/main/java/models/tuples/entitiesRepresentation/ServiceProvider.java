/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package models.tuples.entitiesRepresentation;

import models.enums.ServiceProviderStatus;
import models.enums.UserType;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * This is the entity representation Service_provider class.
 * Service provider in the database relates one to many bookingSlots and one to one Location.
 * Also, it extends from AbstractUser, therefor, it has all the attributes in the table users.
 * So, this class is a junction bettween users, service_provider, location and bookingslots table
 */
public class ServiceProvider extends AbstraticUser {

    /**
     * LIST OF ATTRIBUTES/ COLUMNS
     */
    private String companyFullName;
    private ServiceProviderStatus status;
    private Location location; // ONE TO ONE
    private List<BookingSlots> bookingSlots; // ONE TO MANY

    /**
     * FULL CONSTRUCTOR
     * @param id
     * @param email
     * @param password
     * @param dateCreated
     * @param companyFullName
     * @param status
     * @param location
     * @param bookingSlots
     * @param listOfLogs
     * @param phones
     */
    public ServiceProvider(String id, String email, String password, Date dateCreated,
                           String companyFullName, ServiceProviderStatus status,
                           Location location, List<BookingSlots> bookingSlots,
                           List<Log> listOfLogs,  List<Phone> phones
    ) {
        super(id, email, password, UserType.SERVICE_PROVIDER, dateCreated, listOfLogs, phones);
        this.companyFullName = companyFullName;
        this.status = status;
        this.location = location;
        this.bookingSlots = bookingSlots;
    }


    /**
     * EMPTY CONSTRUCTOR
     */
    public ServiceProvider() {
        userType = UserType.SERVICE_PROVIDER;
        dateCreated = new Date(System.currentTimeMillis());
    }

    /**
     * GETTERS
     * @return
     */
    public String getCompanyFullName() {
        return companyFullName;
    }

    public ServiceProviderStatus getStatus() {
        return status;
    }

    public Location getLocation() {
        return location;
    }

    public List<BookingSlots> getBookingSlots() {
        return bookingSlots;
    }

    /**
     * SETTERS
     * @param newId
     * @return
     */

    @Override
    public ServiceProvider withId(String newId) {
        id = newId;
        return this;
    }

    @Override
    public ServiceProvider withEmail(String newEmail) {
        email = newEmail;
        return this;
    }

    @Override
    public ServiceProvider withPassword(String newPassword) {
        password = newPassword;
        return this;
    }

    @Override
    public ServiceProvider withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public ServiceProvider withDateCreated(Date newDateCreated) {
        dateCreated = newDateCreated;
        return this;
    }

    @Override
    public ServiceProvider withListOfLogs(List<Log> newListOfLog) {
        listOfLogs = newListOfLog;
        return this;
    }

    @Override
    public ServiceProvider withListOfPhones(List<Phone> newListOfPhones) {
        phones = newListOfPhones;
        return this;
    }

    public ServiceProvider withCompanyFullName(String newName) {
        companyFullName = newName;
        return this;
    }

    public ServiceProvider withServiceProviderStatus(ServiceProviderStatus newStatus) {
        status = newStatus;
        return this;
    }

    public ServiceProvider withBookingSlots(List<BookingSlots> bookingSlots) {
        this.bookingSlots = bookingSlots;
        return this;
    }

    public ServiceProvider withLocation(Location newLocation) {
        this.location = newLocation;
        return this;
    }

    /**
     * EQUALS
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ServiceProvider that = (ServiceProvider) o;
        return Objects.equals(companyFullName, that.companyFullName) &&
                status == that.status &&
                Objects.equals(location, that.location) &&
                Objects.equals(bookingSlots, that.bookingSlots);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyFullName, status, location, bookingSlots);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "ServiceProvider{" +
                "companyFullName='" + companyFullName + '\'' +
                ", status=" + status +
                ", location=" + location +
                ", bookingSlots=" + bookingSlots +
                '}';
    }


    public BookingSlots getBookingByTimestamp(Timestamp timestamp) {

        for (BookingSlots slot :
                bookingSlots) {
            if (slot.getTimestamp().equals(timestamp)){
                return slot;
            }
        }
        return null;
    }

    public void addBookingToSlot(Booking b, Timestamp timestamp) {
        bookingSlots.forEach(slot -> {
            if(slot.getTimestamp().equals(timestamp)){
                slot.withBooking(b);
            }
        });
    }


}
