package controllers;

import controllers.dashboards.DashboardController;
import models.users.AbstraticUser;

public class Application {

    private LoginController loginController;
    private FormController formController;
    private DashboardController dashboardController;

    private AbstraticUser user;

    public Application() {
        this.loginController = new LoginController(this);
        //dashboardController = new DashboardController(this);

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

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void login() {
        loginController = new LoginController(this);
    }
}
