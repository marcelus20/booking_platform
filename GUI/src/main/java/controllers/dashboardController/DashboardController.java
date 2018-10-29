package controllers.dashboardController;

import controllers.Application;
import controllers.loginControllers.LoginController;
import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import models.users.AbstractUser;
import utils.ArrayListBuilder;
import views.dashboard.Dashboard;

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

    private DashboardController(AbstractUser user, Application app) {
        this.user = user;
        dashboard = new Dashboard();
        this.app = app;
        config();
        setSizes();
        build();

    }

    public static DashboardController initDashBoardController (AbstractUser user, Application app){
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



    }

    private String displayMessage(){
        return new StringBuilder().append("Hello, ").append(user.geteMail()).toString();
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
        dashboard.add(dashboard.getMenu(), BorderLayout.NORTH);
        dashboard.add(dashboard.getSideBar(), BorderLayout.WEST);
        dashboard.add(dashboard.getOutput(), BorderLayout.EAST);
        dashboard.add(dashboard.getFooter(), BorderLayout.SOUTH);

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
