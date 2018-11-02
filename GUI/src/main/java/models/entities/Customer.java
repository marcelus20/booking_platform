package models.entities;

import models.users.User;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Customer extends User {

    private String firstName;
    private String lastName;
    private List<Bookings> bookings;

    public Customer(Long id, String eMail,String password, String phone,
                    Date dateOfAccountCreation, String firstName, String lastName,
                    List<Bookings> bookings) {
        super(id, eMail, password, phone, dateOfAccountCreation);
        this.firstName = firstName;
        this.lastName = lastName;
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
                Objects.equals(bookings, customer.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, bookings);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bookings=" + bookings +
                ", phone='" + phone + '\'' +
                ", dateOfAccountCreation=" + dateOfAccountCreation +
                ", id=" + id +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
