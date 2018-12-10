package models.tuples.entitiesRepresentation;

import models.enums.UserType;

import java.sql.Date;
import java.util.List;

/**
 * THIS class is represents a user recorded in users table in database that has a user type of admin,
 * therefore it exenteds abstract user. Admins has no extra attributes aside from the ones extended from
 * Abstract user.
 */

public class Admin extends AbstraticUser {

    /**
     * constructor empty
     */
    public Admin (){
        super();
        userType = UserType.ADMIN; // seting user type to admin
        dateCreated = new Date(System.currentTimeMillis()); // seting the created date attribute
    }

    /**
     * FULL CONSTRUCCTOR
     * @param id COLUMN
     * @param email COLUMN
     * @param password COLUMN
     * @param listOfLogs COLUMN
     * @param phones COLUMN
     */
    public Admin(String id, String email, String password, List<Log> listOfLogs, List<Phone> phones) {
        super(id, email, password, UserType.ADMIN, new Date(System.currentTimeMillis()), listOfLogs, phones);
    }

    /**
     * SETTERS
     * @param newId
     * @return
     */
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
    public Admin withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public  Admin withDateCreated(Date newDateCreated) {
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

    // AS ADMIN IS AN EXACT COPY OF ABSTRACT USER, IT HAS NOT HASHCODE OR TOSTRING, IT WILL INSTEAD USE
    //THE ONES CONTAINED IN THE SUPER CLASS
}
