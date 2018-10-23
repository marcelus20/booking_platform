package models.users;

import models.Bookings;
import models.Complaints;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Customer extends User {

    private String firstName;
    private String lastName;
    private List<Complaints> complaints;
    private List<Bookings> bookings;

    public Customer(Long id, String eMail,String password, String phone,
                    Date dateOfAccountCreation, String firstName, String lastName,
                    List<Complaints> complaints, List<Bookings> bookings) {
        super(id, eMail, password, phone, dateOfAccountCreation);
        this.firstName = firstName;
        this.lastName = lastName;
        this.complaints = complaints;
        this.bookings = bookings;
    }

    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Complaints> getComplaints() {
        return complaints;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }



    public Customer withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Customer withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Customer withComplaints(List<Complaints> complaints) {
        this.complaints = complaints;
        return this;
    }

    public Customer withBookings(List<Bookings> bookings) {
        this.bookings = bookings;
        return this;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, complaints, bookings);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", complaints=" + complaints +
                ", bookings=" + bookings +
                ", phone='" + phone + '\'' +
                ", dateOfAccountCreation=" + dateOfAccountCreation +
                ", id=" + id +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
