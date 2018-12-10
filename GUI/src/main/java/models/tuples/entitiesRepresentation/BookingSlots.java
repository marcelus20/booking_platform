package models.tuples.entitiesRepresentation;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents the table BookingSlots in the database.
 */
public class BookingSlots {

    /**
     * list of table columns/attributes
     */
    private Timestamp timestamp;
    private String serviceId;
    private Boolean availability;
    private Booking booking; // ONE TO ONE RELATIONSHIP

    /**
     * FULL CONSTRUCTOR
     * @param timestamp
     * @param serviceId
     * @param availability
     * @param booking
     */
    public BookingSlots(Timestamp timestamp, String serviceId, Boolean availability, Booking booking) {
        this.timestamp = timestamp;
        this.serviceId = serviceId;
        this.availability = availability;
        this.booking = booking;
    }

    /**
     * Empty constructor
     */
    public BookingSlots() {
    }


    /**
     * GETTERS
     * @return
     */
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

    /**
     * SETTERS
     * @param timestamp
     * @return
     */
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

    /**
     * EQUALS
     * @param o
     * @return
     */
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

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(timestamp, serviceId, availability, booking);
    }

    /**
     * TOSTRING
     * @return
     */
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
