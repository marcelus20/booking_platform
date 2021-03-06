/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package models.tuples.entitiesRepresentation;

import models.enums.UserType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ENTITY REPRESETATION OF THE CUSTOMER ENTITY.
 * Each user customer type is linked to a the table Customer in a
 * one to one relationShip.
 */
public class Customer extends AbstraticUser {

    /**
     * ASIDE FROM THE ONES IN THE ABSTRACT USER,
     * HERE IS THE EXTRA ATTRIBUTES / TABLE COLUMNS
     */
    private String firstName;
    private String lastName;
    private List<Complaint> complaints; // ONE TO MANY COMPLAINTS
    private List<Booking> bookings; // ONE TO MANY BOOKINGS

    /**
     * FULL CONSTRUCTOR
     * @param id COLUMN
     * @param email COLUMN
     * @param password COLUMN
     * @param dateCreated COLUMN
     * @param listOfLogs COLUMN
     * @param firstName COLUMN
     * @param lastName COLUMN
     * @param phones COLUMN
     * @param complaints COLUMN
     * @param booking COLUMN
     */
    public Customer(String id, String email, String password, Date dateCreated, List<Log> listOfLogs,
                    String firstName, String lastName, List<Phone>phones, List<Complaint> complaints,
                    List<Booking> booking
    ) {
        super(id, email, password, UserType.CUSTOMER, dateCreated, listOfLogs, phones);
        this.firstName = firstName;
        this.lastName = lastName;
        this.complaints = complaints;
        bookings = booking;
    }

    /**
     * EMPTY CONSTRUCTOR
     */
    public Customer() {
        userType = UserType.CUSTOMER; //DEFAULT
        dateCreated = new Date(System.currentTimeMillis());
    }

    /**
     * GETTERS
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * SETTERS
     * @param newId
     * @return
     */
    @Override
    public Customer withId(String newId) {
        id = newId;
        return this;
    }

    @Override
    public Customer withEmail(String newEmail) {
        email = newEmail;
        return this;
    }

    @Override
    public Customer withPassword(String newPassword) {
        password = newPassword;
        return this;
    }

    @Override
    public Customer withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public Customer withDateCreated(Date newDateCreated) {
        dateCreated = newDateCreated;
        return this;
    }

    @Override
    public Customer withListOfLogs(List<Log> newListOfLog) {
        listOfLogs = newListOfLog;
        return this;
    }

    @Override
    public Customer withListOfPhones(List<Phone> newListOfPhones) {
        phones = newListOfPhones;
        return this;
    }

    public Customer withFirstName(String newName) {
        firstName = newName;
        return this;
    }

    public Customer withLastName(String newName) {
        lastName = newName;
        return this;
    }

    public Customer withComplaints(List<Complaint> newComplaints) {
        complaints = newComplaints;
        return this;
    }

    public Customer withBookings(List<Booking> newBookings) {
        bookings = newBookings;
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
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(complaints, customer.complaints) &&
                Objects.equals(bookings, customer.bookings);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, complaints, bookings);
    }

    /**
     * TOSTRING
     * @return
     */

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", complaints=" + complaints +
                ", bookings=" + bookings +
                '}';
    }

    public Customer withListOfPhones(Phone phone) {
        if(phones == null){
            phones = new ArrayList<>();
        }
        phones.add(phone);
        return this;
    }
}
