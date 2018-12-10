/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

package controllers;

import controllers.dashboards.AdminDashboardController;
import controllers.dashboards.CustomerDashboardController;
import controllers.dashboards.ServiceDashBoardController;
import models.tuples.entitiesRepresentation.AbstraticUser;

import java.sql.SQLException;

/**
 * This is the controller of the whole application. When user is logged in, this class
 * will lead user to its correct dashboard, give user its privileges to browse system.
 *
 * This class controls the Log in operation, Service provider, Customer and admins dashboards initialisation.
 * */

public class Application {


    /**
     * attributes
     */
    private LoginController loginController;
    private FormController formController;
    private CustomerDashboardController customerDashboardController;
    private ServiceDashBoardController serviceDashBoardController;
    private AbstraticUser user;
    private AdminDashboardController adminDashboardController;


    /**
     * CONSTRUCTOR:
     *
     * Initially, just the loginController will be populated with a instance, all other attributes will be
     * initialised as user logs in.
     */
    public Application(){
        this.loginController = new LoginController(this);
    }


    /**
     * Getter
     * @return the user itself
     */
    public AbstraticUser getUser() {
        return user;
    }

    /**
     * Seter for the user: to be used in LoginController when a new user logs in
     * @param user
     */
    public void setUser(AbstraticUser user) {
        this.user = user;
    }

    /**
     * Setter for Form Controller: Happens to be populated when user is not logged in and want to subscribe
     * @param formController
     */
    public void setFormController(FormController formController) {
        this.formController = formController;
    }

    /**
     * Setter for CustomerDashboardController -> to be initialised if user logged in is a Customer
     * @param customerDashboardController
     */
    public void setCustomerDashboardController(CustomerDashboardController customerDashboardController) {
        this.customerDashboardController = customerDashboardController;
    }

    /**
     * Setter for ServiceDashboardController -> to be initialised if user logged in is a Service Provider type
     * @param serviceDashBoardController
     */
    public void setServiceDashBoardController(ServiceDashBoardController serviceDashBoardController) {
        this.serviceDashBoardController = serviceDashBoardController;
    }

    /**
     * A function to call loginController when a user logs in order to be led to the login window
     */
    public void login() {
        loginController = new LoginController(this);
    }

    /**
     * A function to initialise AdminDashboardController if user logged in is an admin type.
     * @param adminDashboardController
     */
    public void setAdminDashboardController(AdminDashboardController adminDashboardController) {
        this.adminDashboardController = adminDashboardController;
    }
}
