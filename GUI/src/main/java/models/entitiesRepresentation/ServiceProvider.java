package models.entitiesRepresentation;

import models.users.User;
import java.sql.Date;
import java.util.Objects;

public class ServiceProvider extends User {

    private String companyFullName;
    private String approvedStatus;
    private Location location;


    public ServiceProvider(String id, String name, String password, String phone, Date dateCreated, String companyFullName, String approvedStatus, Location location) {
        super(id, name, password, phone, dateCreated);
        this.companyFullName = companyFullName;
        this.approvedStatus = approvedStatus;
        this.location = location;
    }

    public ServiceProvider() {
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public String getApprovedStatus() {
        return approvedStatus;
    }

    public Location getLocation() {
        return location;
    }

    public ServiceProvider withCompanyFullName(String newName) {
        companyFullName = newName;
        return this;
    }

    public ServiceProvider withApprovedStatus(String newStatus) {
        approvedStatus = newStatus;
        return this;
    }

    public ServiceProvider withLocation(Location newLocation) {
        location = newLocation;
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
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyFullName, approvedStatus, location);
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "companyFullName='" + companyFullName + '\'' +
                ", approvedStatus='" + approvedStatus + '\'' +
                ", location=" + location +
                '}';
    }
}
