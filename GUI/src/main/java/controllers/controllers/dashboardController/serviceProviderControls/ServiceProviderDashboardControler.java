package controllers.controllers.dashboardController.serviceProviderControls;

import controllers.controllers.Controlls;
import models.entities.ServiceProvider;
import models.users.AbstractUser;
import views.dashboard.Dashboard;

import java.sql.SQLException;

import static controllers.controllers.dashboardController.serviceProviderControls.BookingSlotsController.initBookingSlotsController;

public class ServiceProviderDashboardControler {

    private BookingSlotsController bookingSlotsController;
    private Dashboard dashboard;

    public ServiceProviderDashboardControler(Dashboard dashboard, ServiceProvider user) throws SQLException {
        this.dashboard = dashboard;
        this.bookingSlotsController = initBookingSlotsController(user);
        dashboard.getOutput().add(bookingSlotsController.getViewObject());
    }

    public static ServiceProviderDashboardControler initServiceProviderDashboardController(Dashboard dashboard, ServiceProvider user) throws SQLException {
        return new ServiceProviderDashboardControler(dashboard, user);
    }
}
