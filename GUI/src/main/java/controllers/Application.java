package controllers;

import models.users.AbstraticUser;

public class Application {

    private LoginController loginController;
    private FormController formController;

    private AbstraticUser user;

    public Application() {
        this.loginController = new LoginController(this);
    }

    public AbstraticUser getUser() {
        return user;
    }

    public void setUser(AbstraticUser user) {
        this.user = user;
    }

    public void setFormController(FormController formController) {
        this.formController = formController;
    }


    public void login() {
        loginController = new LoginController(this);
    }
}
