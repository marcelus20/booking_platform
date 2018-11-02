package models.entities;



import models.users.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceProvider extends User {

    private String companyFullName;
    private String approvedStatus;
    private Location location;
    private List<Bookings> bookings;

    public ServiceProvider(Long id, String eMail, String password, String phone, Date dateOfAccountCreation, String companyFullName, String approvedStatus, Location location, List<Bookings> bookings) {
        super(id, eMail, password, phone, dateOfAccountCreation);
        this.companyFullName = companyFullName;
        this.approvedStatus = approvedStatus;
        this.location = location;
        this.bookings = bookings;
    }

    public ServiceProvider() {
        approvedStatus = "pendent";
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public Location getLocations() {
        return location;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public ServiceProvider withCompanyFullName(String companyFullName){
        this.companyFullName = companyFullName;
        return this;
    }
    public ServiceProvider withApprovedStatus(String approvedStatus){
        this.companyFullName = approvedStatus;
        return this;
    }
    public ServiceProvider withLocation(Location location){
        this.location = location;
        return this;
    }
    public ServiceProvider withBookings(List<Bookings> bookings){
        this.bookings = bookings;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ServiceProvider that = (ServiceProvider) o;
        return Objects.equals(companyFullName, that.companyFullName) &&
                Objects.equals(approvedStatus, that.approvedStatus) &&
                Objects.equals(location, that.location) &&
                Objects.equals(bookings, that.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyFullName, approvedStatus, location, bookings);
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "companyFullName='" + companyFullName + '\'' +
                ", approvedStatus='" + approvedStatus + '\'' +
                ", locations=" + location +
                ", bookings=" + bookings +
                '}';
    }
}
