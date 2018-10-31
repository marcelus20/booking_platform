package controllers.dashboardController.adminControlls;

import static controllers.dashboardController.adminControlls.TableOfEntitiesController.initTableOfEntitiesController;

import controllers.dashboardController.DashboardController;
import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import repository.CustomerRepository;
import repository.ServiceProviderRepository;
import views.dashboard.adminView.AdminDashboardView;
import views.dashboard.adminView.ListOfCustomers;
import views.dashboard.adminView.ListOfServices;
import views.dashboard.adminView.TableOfEntities;

import java.awt.*;
import java.sql.SQLException;

public class AdminDashboardController
        implements Controlls<AdminDashboardView>, ViewsObjectGetter<AdminDashboardView> {

    private final AdminDashboardView adminDashboardView;

    private ListOfCustomers listOfCustomer;
    private ListOfServices listOfServices;

    private AdminDashboardController () throws SQLException {
        this.adminDashboardView = new AdminDashboardView();

        listOfCustomer = initTableOfEntitiesController(new ListOfCustomers(),new CustomerRepository())
                .getViewObject();
        listOfServices = initTableOfEntitiesController
                (new ListOfServices(), new ServiceProviderRepository())
                .getViewObject();
        config();
        setSizes();
        build();

    }

    public static AdminDashboardController initAdminDashboardViewController() throws SQLException {
        return new AdminDashboardController();
    }


    @Override
    public void config() {
        adminDashboardView.setLayout(new GridLayout(0,1));

    }

    @Override
    public void build() {
        adminDashboardView.add(adminDashboardView.getToggleListOfCustomers());
        adminDashboardView.add(adminDashboardView.getToggleListOfServiceProviders());

    }

    @Override
    public void setSizes() {

    }

    @Override
    public AdminDashboardView getViewObject() {
        return adminDashboardView;
    }

    public TableOfEntities getListOfCustomer() {
        return listOfCustomer;
    }

    public ListOfServices getListOfServices() {
        return listOfServices;
    }
}
