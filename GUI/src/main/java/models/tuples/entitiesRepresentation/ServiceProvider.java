package models.tuples.entitiesRepresentation;

import models.enums.ServiceProviderStatus;
import models.enums.UserType;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ServiceProvider extends AbstraticUser {

    private String companyFullName;
    private ServiceProviderStatus status;
    private Location location;
    private List<BookingSlots> bookingSlots;

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

    public ServiceProvider() {
        userType = UserType.SERVICE_PROVIDER;
        dateCreated = new Date(System.currentTimeMillis());
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyFullName, status, location, bookingSlots);
    }

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
