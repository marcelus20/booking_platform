package models.users;


import models.Bookings;
import models.Complaints;
import models.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceProvider extends User{

    private String companyFullName;
    private List<Complaints> complaints;
    private List<Location> locations;
    private List<Bookings> bookings;

    public ServiceProvider(String companyFullName, List<Complaints> complaints, List<Location> locations, List<Bookings> bookings) {
        this.companyFullName = companyFullName;
        this.complaints = complaints;
        this.locations = locations;
        this.bookings = bookings;
    }

    public ServiceProvider() {
        locations = new ArrayList<>();
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public List<Complaints> getComplaints() {
        return complaints;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }



    public ServiceProvider withCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName;
        return this;
    }

    public ServiceProvider withComplaints(List<Complaints> complaints) {
        this.complaints = complaints;
        return this;
    }

    public ServiceProvider withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }

    public ServiceProvider withLocations(Location location) {
        this.locations.add(location);
        return this;
    }

    public ServiceProvider withBookings(List<Bookings> bookings) {
        this.bookings = bookings;
        return this;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "companyFullName='" + companyFullName + '\'' +
                ", complaints=" + complaints +
                ", locations=" + locations +
                ", bookings=" + bookings +
                ", phone='" + phone + '\'' +
                ", dateOfAccountCreation=" + dateOfAccountCreation +
                ", id=" + id +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
