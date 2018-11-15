package models.tuples.entitiesRepresentation;

import models.utils.UserType;

import java.sql.Date;
import java.util.Objects;

public abstract class AbstraticUser {

    protected String id;
    protected String email;
    protected String password;
    protected UserType userType;
    protected Date dateCreated;

    public AbstraticUser(String id, String email, String password, UserType userType, Date dateCreated) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.dateCreated = dateCreated;
    }

    public AbstraticUser() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public UserType getUserType() {
        return userType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public abstract AbstraticUser withId(String newId);
    public abstract AbstraticUser withEmail(String newEmail);
    public abstract AbstraticUser withPassword(String newPassword);
    public abstract AbstraticUser withUserType(UserType userType);
    public abstract AbstraticUser withUserCreated(Date newDateCreated);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstraticUser that = (AbstraticUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                userType == that.userType &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, userType, dateCreated);
    }

    @Override
    public String toString() {
        return "AbstraticUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
