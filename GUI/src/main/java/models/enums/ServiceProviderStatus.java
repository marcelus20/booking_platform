package models.enums;

/**
 * This enum is to be used by a service provider object that is either pendent, approved or reject.
 * Admins will handle this enum to set status for the new registered service provider.
 */
public enum ServiceProviderStatus {
    PENDENT, APPROVED, REJECTED;
}
