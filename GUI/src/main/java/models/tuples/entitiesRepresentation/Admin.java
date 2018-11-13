package models.tuples.entitiesRepresentation;

import models.users.AbstraticUser;

public class Admin extends AbstraticUser {

    public Admin (){

    }

    public Admin(String id, String email, String password) {
        super(id, email, password);
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

        return this;
    }
}
