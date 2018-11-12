package models.entitiesRepresentation;

import models.users.User;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ServiceProvider extends User {

    private String companyFullName;
    private String approvedStatus;
    private Location location;
    private List<BookingSlots> bookingSlots;


    public ServiceProvider(String id, String name, String password, String phone, Date dateCreated, String companyFullName, String approvedStatus, Location location, List<BookingSlots> bookingSlots) {
        super(id, name, password, phone, dateCreated);
        this.companyFullName = companyFullName;
        this.approvedStatus = approvedStatus;
        this.location = location;
        this.bookingSlots = bookingSlots;
    }

    public ServiceProvider() {
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public Location getLocation() {
        return location;
    }

    public List<BookingSlots> getBookingSlots() {
        return bookingSlots;
    }

    public ServiceProvider withCompanyFullName(String newName) {
        companyFullName = newName;
        return this;
    }

    public ServiceProvider withApprovedStatus(String newStatus) {
        approvedStatus = newStatus;
        return this;
    }

    public ServiceProvider withLocation(Location newLocation) {
        location = newLocation;
        return this;
    }

    public ServiceProvider withBookingSlots(List<BookingSlots> bookingSlots) {
        this.bookingSlots = bookingSlots;
        return this;
    }

    public ServiceProvider withBookingSlots(BookingSlots bookingSlots) {
        this.bookingSlots.add(bookingSlots);
        return this;
    }

    public BookingSlots getBookingByTimestamp(Timestamp timestamp){
        BookingSlots b = null;

        for(BookingSlots slot: bookingSlots){
            if(slot.getTimestamp().equals(timestamp)){
                b = slot;
            }
        }
        System.out.println(b);

        return b;
    }

    public void addBookingToSlot(Booking b, Timestamp t){
        bookingSlots.forEach(slot -> {
            if(slot.getTimestamp().equals(t)){
                slot.withBooking(b);
            }
        });
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ServiceProvider that = (ServiceProvider) o;
        return Objects.equals(companyFullName, that.companyFullName) &&
                Objects.equals(approvedStatus, that.approvedStatus) &&
                Objects.equals(location, that.location) &&
                Objects.equals(bookingSlots, that.bookingSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyFullName, approvedStatus, location, bookingSlots);
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "companyFullName='" + companyFullName + '\'' +
                ", approvedStatus='" + approvedStatus + '\'' +
                ", location=" + location +
                ", bookingSlots=" + bookingSlots +
                '}';
    }
}
