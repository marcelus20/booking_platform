package models.entities;

import models.users.AbstractUser;

import java.util.Objects;

public class Admin extends AbstractUser {


    @Override
    public AbstractUser withId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public AbstractUser withEmail(String email) {
        this.eMail = email;
        return this;
    }

    @Override
    public AbstractUser withPassword(String password) {
        this.password = password;
        return this;
    }
}
