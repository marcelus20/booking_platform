package controllers.controllers.dashboardController.adminControlls;
import static controllers.controllers.dashboardController.adminControlls.TableOfEntitiesController.initTableOfEntitiesController;
import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import controllers.controllers.dashboardController.DashboardController;
import controllers.controllers.dashboardController.DashboardController.TableView;
import models.repositories.BookingsRepository;
import models.repositories.CustomerRepository;
import models.repositories.ServiceProviderRepository;
import views.dashboard.Dashboard;
import views.dashboard.adminView.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminDashboardController
        implements Controlls<AdminDashboardView>, ViewsObjectGetter<AdminDashboardView> {

    private final AdminDashboardView adminDashboardView;
    private final Dashboard dashboard;
    private DashboardController.TableView tableView;
    private ListOfCustomers listOfCustomer;
    private ListOfServices listOfServices;

    private AdminDashboardController (Dashboard dashboard) throws SQLException {
        this.adminDashboardView = new AdminDashboardView();
        this.dashboard = dashboard;
        listOfCustomer = initTableOfEntitiesController(new ListOfCustomers(),new CustomerRepository())
                .getViewObject();
        listOfServices = initTableOfEntitiesController
                (new ListOfServices(), new ServiceProviderRepository())
                .getViewObject();
    }

    public static AdminDashboardController initAdminDashboardViewController(Dashboard dashboard) throws SQLException {
        return new AdminDashboardController(dashboard);
    }

    private void showListOfCustomers() throws SQLException {
        tableView = TableView.viewinCustomers; dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TablesOfCustomers(new ListOfCustomers(), new CustomerRepository())
                        .getViewObject());

        dashboard.getOutput().repaint(); dashboard.getOutput().validate(); dashboard.repaint(); dashboard.validate();
    }

    private void showListOfServices() throws SQLException {
        tableView = TableView.viewingServices; dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TableOfServiceProviders(new ListOfServices(), new ServiceProviderRepository())
                        .getViewObject());

        dashboard.getOutput().repaint(); dashboard.getOutput().validate(); dashboard.repaint();
        dashboard.validate();
    }

    private void showListOfBookings() throws SQLException {
        tableView = TableView.ViewingBookings; dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TableOfBookings(new ListOfBookings(), new BookingsRepository())
                        .getViewObject());

        dashboard.getOutput().repaint(); dashboard.getOutput().validate(); dashboard.repaint();
        dashboard.validate();
    }

    private void giveFunctionToButtons(){
        assignFunctionToToggleCustomer();
        assignFunctionToToggleServices();
        assignFunctionToToggleBookings();
        assignFunctionToRefreash();
    }

    private void assignFunctionToToggleCustomer(){
        adminDashboardView.getToggleListOfCustomers().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showListOfCustomers();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void assignFunctionToToggleServices(){
        adminDashboardView.getToggleListOfServiceProviders().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showListOfServices();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void assignFunctionToToggleBookings(){
        adminDashboardView.getSeeBookings().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showListOfBookings();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void assignFunctionToRefreash(){
        adminDashboardView.getRefreash().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableView == TableView.viewinCustomers){
                    try {
                        showListOfCustomers();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }else if(tableView == TableView.viewingServices){
                    try {
                        showListOfServices();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }else if(tableView == TableView.ViewingBookings){
                    try {
                        showListOfBookings();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public TableOfEntities getListOfCustomer() {
        return listOfCustomer;
    }

    public ListOfServices getListOfServices() {
        return listOfServices;
    }

    @Override
    public void config() {
        adminDashboardView.setLayout(new GridLayout(0,1));
        giveFunctionToButtons();
    }

    @Override
    public void build() {
        adminDashboardView.add(adminDashboardView.getToggleListOfCustomers());
        adminDashboardView.add(adminDashboardView.getToggleListOfServiceProviders());
        adminDashboardView.add(adminDashboardView.getSeeBookings());

    }

    @Override
    public void setSizes() {

    }

    @Override
    public AdminDashboardView getViewObject() {
        return adminDashboardView;
    }


}
