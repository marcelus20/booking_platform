package controllers;

import controllers.controllers.dashboardController.DashboardController;
import controllers.controllers.loginControllers.LoginController;
import controllers.controllers.signUpFormControllers.FormsController;
import models.users.AbstractUser;

import java.sql.SQLException;

public class Application {

    protected AbstractUser user;
    private LoginController login;
    private DashboardController dashboard;
    private FormsController forms;


    public Application() throws SQLException {
        LoginController.initLoginController(this);

    }

    public AbstractUser getUser() {
        return user;
    }

    public void setUser(AbstractUser user) {
        this.user = user;
    }

    public LoginController getLogin() {
        return login;
    }

    public void setLogin(LoginController login) {
        this.login = login;
    }

    public DashboardController getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    public FormsController getForms() {
        return forms;
    }

    public void setForms(FormsController forms) {
        this.forms = forms;
    }

}
