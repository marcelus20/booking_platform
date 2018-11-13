package models.tuples.entitiesRepresentation;

import java.util.Objects;

public class Booking {

    private String customerId;
    private String bookingStatus;
    private String complaint;

    public Booking(String bookingStatus, String complaint) {
        this.bookingStatus = bookingStatus;
        this.complaint = complaint;
    }

    public Booking() {
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Booking withBookingStatus(String newStatus){
        bookingStatus = newStatus;
        return this;
    }

    public Booking withComplaint(String newComplaint){
        complaint = newComplaint;
        return this;
    }

    public Booking withCustomerId(String customer_id) {
        customerId = customer_id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingStatus, booking.bookingStatus) &&
                Objects.equals(complaint, booking.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingStatus, complaint);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingStatus='" + bookingStatus + '\'' +
                ", complaint='" + complaint + '\'' +
                '}';
    }


}
