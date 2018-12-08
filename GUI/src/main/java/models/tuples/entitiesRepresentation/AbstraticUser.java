package models.tuples.entitiesRepresentation;

import models.enums.UserType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstraticUser {

    protected String id;
    protected String email;
    protected String password;
    protected UserType userType;
    protected Date dateCreated;
    protected List<Log> listOfLogs;
    protected List<Phone> phones;

    public AbstraticUser(String id, String email, String password, UserType userType, Date dateCreated, List<Log> listOfLogs, List<Phone> phones) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.dateCreated = dateCreated;
        this.listOfLogs = listOfLogs;
        this.phones = phones;
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

    public List<Log> getListOfLogs() {
        return listOfLogs;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Phone getPhone(){
        if(phones != null && phones.size() != 0){
            return phones.get(0);
        }
        return null;
    }

    public abstract AbstraticUser withId(String newId);
    public abstract AbstraticUser withEmail(String newEmail);
    public abstract AbstraticUser withPassword(String newPassword);
    public abstract AbstraticUser withUserType(UserType userType);
    public abstract AbstraticUser withDateCreated(Date newDateCreated);
    public abstract AbstraticUser withListOfLogs(List<Log> newListOfLog);
    public abstract AbstraticUser withListOfPhones(List<Phone> newListOfPhones);
    public AbstraticUser withPhone(Phone phone) {
        if(phones == null){
            phones = new ArrayList<>();
        }
        phones.add(phone);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstraticUser that = (AbstraticUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                userType == that.userType &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(listOfLogs, that.listOfLogs) &&
                Objects.equals(phones, that.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, userType, dateCreated, listOfLogs, phones);
    }

    @Override
    public String toString() {
        return "AbstraticUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", dateCreated=" + dateCreated +
                ", listOfLogs=" + listOfLogs +
                ", phones=" + phones +
                '}';
    }


}