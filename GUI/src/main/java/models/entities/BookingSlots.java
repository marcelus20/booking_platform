package models.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class BookingSlots {

    private Timestamp timestamp;
    private Long serviceId;
    private String availability;
    private Bookings booking;

    public BookingSlots(Timestamp timestamp, Long serviceId, String availability, Bookings booking) {
        this.timestamp = timestamp;
        this.serviceId = serviceId;
        this.availability = availability;
        this.booking = booking;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getAvailability() {
        return availability;
    }

    public Bookings getBooking() {
        return booking;
    }

    public BookingSlots withTimeStamp(Timestamp timeStamp){
        timestamp = timeStamp;
        return this;
    }

    public BookingSlots withServiceId(Long serviceId){
        this.serviceId = serviceId;
        return this;
    }

    public BookingSlots withAvailability(String availability){
        this.availability = availability;
        return this;
    }

    public BookingSlots withBookings(Bookings bookings){
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
                ", serviceId=" + serviceId +
                ", availability='" + availability + '\'' +
                ", booking=" + booking +
                '}';
    }
}

