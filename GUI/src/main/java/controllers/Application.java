package controllers;

import controllers.dashboards.AdminDashboardController;
import controllers.dashboards.CustomerDashboardController;
import controllers.dashboards.ServiceDashBoardController;
import models.tuples.entitiesRepresentation.AbstraticUser;

import java.sql.SQLException;

public class Application {

    private LoginController loginController;
    private FormController formController;
    private CustomerDashboardController customerDashboardController;
    private ServiceDashBoardController serviceDashBoardController;

    private AbstraticUser user;
    private AdminDashboardController adminDashboardController;

    public Application(){
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

    public void setCustomerDashboardController(CustomerDashboardController customerDashboardController) {
        this.customerDashboardController = customerDashboardController;
    }

    public void setServiceDashBoardController(ServiceDashBoardController serviceDashBoardController) {
        this.serviceDashBoardController = serviceDashBoardController;
    }

    public void login() {
        loginController = new LoginController(this);
    }

    public void setAdminDashboardController(AdminDashboardController adminDashboardController) {
        this.adminDashboardController = adminDashboardController;
    }
}
