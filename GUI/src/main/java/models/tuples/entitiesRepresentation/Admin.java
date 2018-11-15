package models.tuples.entitiesRepresentation;

import models.utils.UserType;

import java.sql.Date;

public class Admin extends AbstraticUser {

    public Admin (){
        super();
        withUserType(UserType.ADMIN);
        withUserCreated(new Date(System.currentTimeMillis()));
    }

    public Admin(String id, String email, String password) {
        super(id, email, password, UserType.ADMIN, new Date(System.currentTimeMillis()));
    }

    @Override
    public AbstraticUser withId(String newId) {
        id = newId;
        return this;
    }

    @Override
    public AbstraticUser withEmail(String newEmail) {
        email = newEmail;
        return this;
    }

    @Override
    public AbstraticUser withPassword(String newPassword) {
        password = newPassword;
        return this;
    }

    @Override
    public AbstraticUser withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public AbstraticUser withUserCreated(Date newDateCreated) {
        dateCreated = newDateCreated;
        return this;
    }

}
