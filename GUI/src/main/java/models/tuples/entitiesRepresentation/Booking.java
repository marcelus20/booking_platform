package models.tuples.entitiesRepresentation;

import models.enums.BookingReview;
import models.enums.BookingStatus;

import java.util.Objects;

public class Booking {

    private String customerId;
    private BookingStatus bookingStatus;
    private BookingReview review;

    public Booking(BookingStatus bookingStatus, BookingReview review) {
        this.bookingStatus = bookingStatus;
        this.review = review;
    }

    public Booking() {
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public BookingReview getReview() {
        return review;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Booking withBookingStatus(BookingStatus newStatus){
        bookingStatus = newStatus;
        return this;
    }

    public Booking withReview(BookingReview newReview){
        review = newReview;
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
                Objects.equals(review, booking.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingStatus, review);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingStatus='" + bookingStatus + '\'' +
                ", review='" + review + '\'' +
                '}';
    }


}
