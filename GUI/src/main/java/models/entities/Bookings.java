package models.entities;



import java.sql.Timestamp;

import java.util.Objects;

public class Bookings {

    private Long customerId;
    private Long serviceId;
    private Timestamp timestamp;
    private String bookingStatus;
    private String complaint;

    public Bookings(Long customerId, Long serviceId, Timestamp timestamp, String bookingStatus, String complaint) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.timestamp = timestamp;
        this.bookingStatus = bookingStatus;
        this.complaint = complaint;
    }

    public Bookings() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getComplaint() {
        return complaint;
    }

    public Bookings withCustomerId(Long customerId){
        this.customerId = customerId;
        return this;
    }

    public Bookings withServiceId(Long serviceId){
        this.serviceId = serviceId;
        return this;
    }

    public Bookings withTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public Bookings withBookingStatus(String bookingStatus){
        this.bookingStatus = bookingStatus;
        return this;
    }

    public Bookings withComplaint(String complaint){
        this.complaint = complaint;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookings bookings = (Bookings) o;
        return Objects.equals(customerId, bookings.customerId) &&
                Objects.equals(serviceId, bookings.serviceId) &&
                Objects.equals(timestamp, bookings.timestamp) &&
                Objects.equals(bookingStatus, bookings.bookingStatus) &&
                Objects.equals(complaint, bookings.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, serviceId, timestamp, bookingStatus, complaint);
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", timestamp=" + timestamp +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}
