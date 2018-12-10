package models.tuples.joinedEntities;

import models.enums.BookingReview;
import models.enums.BookingStatus;
import models.tuples.entitiesRepresentation.Customer;
import models.tuples.entitiesRepresentation.ServiceProvider;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * this model class is a representation of a joined entities customer, service_peroviders and bookings in the database
 */
public class ManageBookingView {

    /**
     * LIST OF COLUMNS
     */
    private ServiceProvider serviceProvider;
    private Customer customer;
    private Timestamp timestamp;
    private String serviceId;
    private String customerId;
    private BookingStatus bookingStatus;
    private String companyName;
    private BookingReview review;
    private String phone;

    /**
     * EMPTY CONSTRUCTOR
     */
    public ManageBookingView() {
    }

    //GETTERS
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

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * SETTERS
     * @param customerId
     */
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

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManageBookingView that = (ManageBookingView) o;
        return Objects.equals(serviceProvider, that.serviceProvider) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(customerId, that.customerId) &&
                bookingStatus == that.bookingStatus &&
                Objects.equals(companyName, that.companyName) &&
                review == that.review &&
                Objects.equals(phone, that.phone);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(serviceProvider, customer, timestamp, serviceId, customerId, bookingStatus, companyName, review, phone);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "ManageBookingView{" +
                "serviceProvider=" + serviceProvider +
                ", customer=" + customer +
                ", timestamp=" + timestamp +
                ", serviceId='" + serviceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", companyName='" + companyName + '\'' +
                ", review=" + review +
                ", phone='" + phone + '\'' +
                '}';
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
