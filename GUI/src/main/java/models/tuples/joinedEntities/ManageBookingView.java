package models.tuples.joinedEntities;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import java.sql.Timestamp;
import java.util.Objects;

public class ManageBookingView {

    private Timestamp timestamp;
    private String serviceId;
    private String customerId;
    private BookingStatus bookingStatus;
    private String companyName;
    private BookingReview review;
    private String phone;

    public ManageBookingView() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BookingReview getReview() {
        return review;
    }

    public void setReview(BookingReview review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManageBookingView that = (ManageBookingView) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(customerId, that.customerId) &&
                bookingStatus == that.bookingStatus &&
                Objects.equals(companyName, that.companyName) &&
                review == that.review;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, serviceId, customerId, bookingStatus, companyName, review);
    }

    @Override
    public String toString() {
        return "ManageBookingView{" +
                "timestamp=" + timestamp +
                ", serviceId='" + serviceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", companyName='" + companyName + '\'' +
                ", review=" + review +
                '}';
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
