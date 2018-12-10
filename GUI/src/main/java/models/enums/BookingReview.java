package models.enums;

/**
 * This enum represents the possible states that an BookingReview object can be assigned.
 * A Booking review can either be with no review, with end of the world, terrible, bad,
 * meh, ok, good, very good, superb or perfect state.
 *
 * Customers when adding a review, they will be prompted to choose either one of these options.
 */
public enum BookingReview {

    NO_REVIEW_ADDED,
    END_OF_THE_WORLD, TERRIBLE, BAD, MEH, OK, GOOD, VERY_GOOD, SUPERB, PERFECT;


}
