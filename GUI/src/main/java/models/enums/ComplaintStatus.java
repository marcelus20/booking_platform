/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

package models.enums;

/**
 * To set a state for the complaint status object. Admins will use it to set either the complaint is beig processed or finished.
 * Initally, when users make the complaint, the complaint itself will be set to PENDENT by default, in the database
 */
public enum ComplaintStatus {
    PENDENT, PROCESSING, FINISHED
}
