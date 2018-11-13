package models.tuples.entitiesRepresentation;

import java.sql.Timestamp;
import java.util.Objects;

public class BookingSlots {

    private Timestamp timestamp;
    private String serviceId;
    private Boolean availability;
    private Booking booking;

    public BookingSlots(Timestamp timestamp, String serviceId, Boolean availability, Booking booking) {
        this.timestamp = timestamp;
        this.serviceId = serviceId;
        this.availability = availability;
        this.booking = booking;
    }

    public BookingSlots() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingSlots withTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BookingSlots withServiceId(String id) {
        serviceId = id;
        return this;
    }

    public BookingSlots withAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }

    public BookingSlots withBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingSlots that = (BookingSlots) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(availability, that.availability) &&
                Objects.equals(booking, that.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, serviceId, availability, booking);
    }

    @Override
    public String toString() {
        return "BookingSlots{" +
                "timestamp=" + timestamp +
                ", serviceId='" + serviceId + '\'' +
                ", availability=" + availability +
                ", booking=" + booking +
                '}';
    }
}
