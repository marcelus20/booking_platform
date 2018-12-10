/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

package models.enums;

/**
 * This enum list the states of a bookingStatus object. I can either be CONFIRMED, PENDENT, COMPLETE OR
 * USER_DID_NOT_ARRIVE. This is to be used by service providers when setting up a status of a booking.
 */
public enum BookingStatus {

    CONFIRMED,
    PENDENT,
    COMPLETE,
    USER_DID_NOT_ARRIVE;

}
