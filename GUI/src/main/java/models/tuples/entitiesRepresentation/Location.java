package models.tuples.entitiesRepresentation;

import java.util.Objects;

/**
 * Location table in the database entity representation.
 */
public class Location {
    /**
     * list of Columns/ attributes
     */
    private String serviceId;
    private String firstLineAddress;
    private String eirCode;
    private String city;
    private String secondLineAddress;


    /**
     * FULL CONSTRUCTOR
     * @param serviceId COLUMN
     * @param firstLineAddress COLUMN
     * @param eirCode COLUMN
     * @param city COLUMN
     * @param secondLineAddress COLUMN
     *
     */
    public Location(String serviceId, String firstLineAddress, String eirCode, String city, String secondLineAddress) {
        this.serviceId = serviceId;
        this.firstLineAddress = firstLineAddress;
        this.eirCode = eirCode;
        this.city = city;
        this.secondLineAddress = secondLineAddress;
    }

    /**
     * EMPTY CONSTRUCTOR
     */
    public Location() {
    }

    /**
     * GETTERS
     * @return
     */
    public String getServiceId() {
        return serviceId;
    }

    public String getFirstLineAddress() {
        return firstLineAddress;
    }

    public String getEirCode() {
        return eirCode;
    }

    public String getCity() {
        return city;
    }

    public String getSecondLineAddress() {
        return secondLineAddress;
    }

    /**
     * SETTERS
     * @param newId
     * @return
     */
    public Location withServiceId(String newId) {
        serviceId = newId;
        return this;
    }

    public Location withFirstLineAddress(String newAddress) {
        firstLineAddress = newAddress;
        return this;
    }

    public Location withEirCode(String newEircode) {
        eirCode = newEircode;
        return this;
    }

    public Location withCity(String newCity) {
        city = newCity;
        return this;
    }

    public Location withSecondLineAddress(String newAddress) {
        secondLineAddress = newAddress;
        return this;
    }

    /**
     * EQUALS
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(serviceId, location.serviceId) &&
                Objects.equals(firstLineAddress, location.firstLineAddress) &&
                Objects.equals(eirCode, location.eirCode) &&
                Objects.equals(city, location.city) &&
                Objects.equals(secondLineAddress, location.secondLineAddress);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(serviceId, firstLineAddress, eirCode, city, secondLineAddress);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "Location{" +
                "serviceId='" + serviceId + '\'' +
                ", firstLineAddress='" + firstLineAddress + '\'' +
                ", eirCode='" + eirCode + '\'' +
                ", city='" + city + '\'' +
                ", secondLineAddress='" + secondLineAddress + '\'' +
                '}';
    }
}
