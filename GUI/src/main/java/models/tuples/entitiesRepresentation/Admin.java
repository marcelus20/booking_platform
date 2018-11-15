package models.tuples.entitiesRepresentation;

import models.enums.UserType;

import java.sql.Date;
import java.util.List;

public class Admin extends AbstraticUser {

    public Admin (){
        super();
        userType = UserType.ADMIN;
        dateCreated = new Date(System.currentTimeMillis());
    }

    public Admin(String id, String email, String password, List<Log> listOfLogs, List<Phone> phones) {
        super(id, email, password, UserType.ADMIN, new Date(System.currentTimeMillis()), listOfLogs, phones);
    }

    @Override
    public Admin withId(String newId) {
        id = newId;
        return this;
    }

    @Override
    public Admin withEmail(String newEmail) {
        email = newEmail;
        return this;
    }

    @Override
    public Admin withPassword(String newPassword) {
        password = newPassword;
        return this;
    }

    @Override
    public  Admin withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public  Admin withUserCreated(Date newDateCreated) {
        dateCreated = newDateCreated;
        return this;
    }

    @Override
    public Admin withListOfLogs(List<Log> newListOfLog) {
        listOfLogs = newListOfLog;
        return this;
    }

    @Override
    public Admin withListOfPhones(List<Phone> newListOfPhones) {
        this.phones = newListOfPhones;
        return this;
    }



}
