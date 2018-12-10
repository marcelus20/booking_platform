package models.enums;

/**
 * This enum is responsible to map the users instance of admin to ADMIN type, customer to CUSTOMER type and service to
 * SERVICE_PROVIDER type.
 * Users classes such as Admin, Customer and ServiceProvider have an attribute userType which turns out to receive a value from this ENUM.
 */
public enum UserType{
    ADMIN, CUSTOMER, SERVICE_PROVIDER;
}
