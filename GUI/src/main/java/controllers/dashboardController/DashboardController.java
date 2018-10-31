package controllers.dashboardController;

import static controllers.dashboardController.adminControlls.AdminDashboardController.initAdminDashboardViewController;

import controllers.Application;
import controllers.dashboardController.adminControlls.TableOfBookings;
import controllers.dashboardController.adminControlls.TableOfServiceProviders;
import controllers.dashboardController.adminControlls.TablesOfCustomers;
import controllers.loginControllers.LoginController;
import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import models.users.AbstractUser;
import models.users.Admin;
import models.users.Customer;
import models.users.ServiceProvider;
import repository.BookingsRepository;
import repository.CustomerRepository;
import repository.ServiceProviderRepository;
import utils.ArrayListBuilder;
import views.dashboard.adminView.AdminDashboardView;
import views.dashboard.Dashboard;
import views.dashboard.adminView.ListOfBookings;
import views.dashboard.adminView.ListOfCustomers;
import views.dashboard.adminView.ListOfServices;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DashboardController implements Controlls<Dashboard>, ViewsObjectGetter<Dashboard> {

    private Dashboard dashboard;
    private AbstractUser user;
    private Application app;
    private AdminDashboardView adminDashboardView;
    private TableView tableView;

    private enum TableView{
        viewinCustomers, viewingServices, ViewingBookings;
    }

    private DashboardController(AbstractUser user, Application app) throws SQLException {
        this.user = user;
        dashboard = new Dashboard();
        this.app = app;
        adminDashboardView = initAdminDashboardViewController().getViewObject();
        config();
        setSizes();
        build();
    }

    public static DashboardController initDashBoardController (AbstractUser user, Application app) throws SQLException {
        return new DashboardController(user, app);
    }


    @Override
    public Dashboard getViewObject() {
        return dashboard;
    }

    @Override
    public void config() {
        makeBorders();
        dashboard.setSize(1280,720);
        dashboard.setVisible(true);
        dashboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dashboard.setLayout(new BorderLayout());

        dashboard.repaint();
        dashboard.validate();

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

    private void showListOfCustomers() throws SQLException {
        tableView = TableView.viewinCustomers;
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TablesOfCustomers(new ListOfCustomers(), new CustomerRepository())
                        .getViewObject());

        dashboard.getOutput().repaint();
        dashboard.getOutput().validate();
        dashboard.repaint();
        dashboard.validate();
    }

    private void showListOfServices() throws SQLException {
        tableView = TableView.viewingServices;
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TableOfServiceProviders(new ListOfServices(), new ServiceProviderRepository())
                        .getViewObject());

        dashboard.getOutput().repaint();
        dashboard.getOutput().validate();
        dashboard.repaint();
        dashboard.validate();
    }

    private void showListOfBookings() throws SQLException {
        tableView = TableView.ViewingBookings;
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(adminDashboardView.getRefreash());
        dashboard.getOutput()
                .add(new TableOfBookings(new ListOfBookings(), new BookingsRepository())
                        .getViewObject());

        dashboard.getOutput().repaint();
        dashboard.getOutput().validate();
        dashboard.repaint();
        dashboard.validate();
    }

    private String displayMessage(){
        if(user instanceof Admin){
            return new StringBuilder().append("Hello, ").append(user.geteMail()).toString();
        }else if (user instanceof ServiceProvider){
            return new StringBuilder().append("Hello, ").append(((ServiceProvider) user).getCompanyFullName()).toString();
        }else{
            return new StringBuilder().append("Hello, ").append(((Customer)user).getFirstName()).toString();
        }

    }

    @Override
    public void build() {
        List<JMenuItem> menuItemList = createJmenuItems();

        JMenu menu = new JMenu("File");

        //adding each item to the menu
        menuItemList.forEach(item->{
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(item.getText().equals("Logout")){
                        try {
                            logout();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            menu.add(item);
        });

        dashboard.getMenu().add(menu);

        dashboard.getSideBar().add(new JLabel(displayMessage()));
        if(user instanceof Admin){
            dashboard.getSideBar().add(adminDashboardView);
        }
        dashboard.add(dashboard.getMenu(), BorderLayout.NORTH);
        dashboard.add(dashboard.getSideBar(), BorderLayout.WEST);
        dashboard.add(dashboard.getOutput(), BorderLayout.EAST);
        dashboard.add(dashboard.getFooter(), BorderLayout.SOUTH);
        dashboard.getFooter().add(new JLabel("CREATED BY: Felipe Mantovani, id: 2017192"));

    }

    @Override
    public void setSizes() {
        dashboard.getSideBar().setPreferredSize(new Dimension(300,600));
        dashboard.getOutput().setPreferredSize(new Dimension(950,600));
        dashboard.getFooter().setPreferredSize(new Dimension(120, 50));

    }

    private List<JMenuItem> createJmenuItems(){
        return new ArrayListBuilder().add(new JMenuItem("File"))
                .add(new JMenuItem("Edit")).add(new JMenuItem("Logout"))
                .build();
    }

    private void makeBorders(){
        dashboard.getSideBar().setBorder(new LineBorder(Color.DARK_GRAY, 2));
        dashboard.getOutput().setBorder(new LineBorder(Color.DARK_GRAY, 2));
        dashboard.getFooter().setBorder(new LineBorder(Color.LIGHT_GRAY));
        dashboard.getSideBar().setBackground(Color.LIGHT_GRAY);
    }

    private void logout() throws SQLException {
        app.setUser(null);
        app.getDashboard().getViewObject().dispose();
        app.setLogin(LoginController.initLoginController(app));
    }

    public void setVisible(){
        dashboard.setVisible(true);
    }

    public void setVisible(Boolean flag){
        dashboard.setVisible(flag);
    }

    public void setInvisible(){
        dashboard.setVisible(false);
    }
}
