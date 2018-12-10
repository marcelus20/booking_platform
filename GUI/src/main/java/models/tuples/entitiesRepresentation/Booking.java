package models.tuples.entitiesRepresentation;

import models.enums.BookingReview;
import models.enums.BookingStatus;

import java.util.Objects;

/**
 * This class is a entity representation of the table booking in the database.
 * the booking Slot table has a relationship one to one, so this entire class is to be
 *  passed as a booking slot attribute.
 */
public class Booking {

    /**
     * LIST OF COLUMNS/ ATTRIBUTES
     */
    private String customerId;
    private BookingStatus bookingStatus;
    private BookingReview review;

    /**
     * FULL CONSTRUCTOR
     * @param bookingStatus COLUMN
     * @param review COLUMN
     */
    public Booking(BookingStatus bookingStatus, BookingReview review) {
        this.bookingStatus = bookingStatus;
        this.review = review;
    }

    /**
     * DEFAULT CONSTRUCTOR
     */
    public Booking() {
    }

    /**
     * GETTERS
     * @return
     */
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public BookingReview getReview() {
        return review;
    }

    public String getCustomerId() {
        return customerId;
    }

    //SETERS
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

    /**
     * EQUALS METHOD
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingStatus, booking.bookingStatus) &&
                Objects.equals(review, booking.review);
    }

    /**
     * HASHCODE METHOD
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(bookingStatus, review);
    }

    /**
     * TOSTRING METHOD
     * @return
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingStatus='" + bookingStatus + '\'' +
                ", review='" + review + '\'' +
                '}';
    }


}
