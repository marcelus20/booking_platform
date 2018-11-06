package controllers.controllers.dashboardController;

import static controllers.controllers.dashboardController.adminControlls.AdminDashboardController.initAdminDashboardViewController;
import controllers.Application;
import controllers.controllers.dashboardController.adminControlls.AdminDashboardController;
import controllers.controllers.dashboardController.customerControls.CustomerDashboardControler;
import controllers.controllers.dashboardController.serviceProviderControls.ServiceProviderDashboardControler;
import controllers.controllers.loginControllers.LoginController;
import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import models.users.AbstractUser;
import models.entities.Admin;
import models.entities.Customer;
import models.entities.ServiceProvider;
import utils.ArrayListBuilder;
import views.dashboard.Dashboard;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import static controllers.controllers.dashboardController.serviceProviderControls.ServiceProviderDashboardControler.initServiceProviderDashboardController;

public class DashboardController implements Controlls<Dashboard>, ViewsObjectGetter<Dashboard> {

    private Dashboard dashboard;
    private AbstractUser user;
    private Application app;
    private AdminDashboardController adminDashboardController;
    private ServiceProviderDashboardControler serviceProviderDashBoardController;
    private CustomerDashboardControler customerDashboardController;

    public enum TableView{
        viewinCustomers, viewingServices, ViewingBookings;
    }

    private DashboardController(AbstractUser user, Application app) throws SQLException {
        this.user = user;
        dashboard = new Dashboard();
        this.app = app;
        redirectToProperPanel();
        config();
        setSizes();
        build();
    }

    public static DashboardController initDashBoardController (AbstractUser user, Application app) throws SQLException {
        return new DashboardController(user, app);
    }

    private List<JMenuItem> createJmenuItems(){
        return new ArrayListBuilder().add(new JMenuItem("File"))
                .add(new JMenuItem("Edit")).add(new JMenuItem("Logout"))
                .build();
    }

    private void redirectToProperPanel() throws SQLException {
        if(user instanceof Admin){
            adminDashboardController = initAdminDashboardViewController(dashboard);
        }else if (user instanceof ServiceProvider){
            serviceProviderDashBoardController = initServiceProviderDashboardController(dashboard, (ServiceProvider) user);
        }else{
            //customerDashboardController =
        }
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

    private String displayMessage(){
        if(user instanceof Admin){
            return new StringBuilder().append("Hello, ").append(user.geteMail()).toString();
        }else if (user instanceof ServiceProvider){
            return new StringBuilder().append("Hello, ").append(((ServiceProvider) user).getCompanyFullName()).toString();
        }else{
            return new StringBuilder().append("Hello, ").append(((Customer)user).getFirstName()).toString();
        }

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

    @Override
    public void config() {
        makeBorders();
        dashboard.setSize(1280,720);
        dashboard.setVisible(true);
        dashboard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dashboard.setLayout(new BorderLayout());
        dashboard.repaint();
        dashboard.validate();
        if(userIsAdmin()){
            adminDashboardController.config();
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
            dashboard.getSideBar().add(adminDashboardController.getViewObject());
        }
        dashboard.add(dashboard.getMenu(), BorderLayout.NORTH);
        dashboard.add(dashboard.getSideBar(), BorderLayout.WEST);
        dashboard.add(dashboard.getOutput(), BorderLayout.EAST);
        dashboard.add(dashboard.getFooter(), BorderLayout.SOUTH);
        dashboard.getFooter().add(new JLabel("CREATED BY: Felipe Mantovani, id: 2017192"));
        if(userIsAdmin()){
            adminDashboardController.build();
        }


    }

    @Override
    public void setSizes() {
        dashboard.getSideBar().setPreferredSize(new Dimension(300,600));
        dashboard.getOutput().setPreferredSize(new Dimension(950,600));
        dashboard.getFooter().setPreferredSize(new Dimension(120, 50));
        if(userIsAdmin()){
            adminDashboardController.setSizes();
        }


    }

    @Override
    public Dashboard getViewObject() {
        return dashboard;
    }

    private Boolean userIsAdmin(){
        if(user instanceof Admin){
            return true;
        }else{
            return false;
        }
    }

    private Boolean userIsServiceProvider(){
        if(user instanceof ServiceProvider){
            return true;
        }else{
            return false;
        }
    }

}
