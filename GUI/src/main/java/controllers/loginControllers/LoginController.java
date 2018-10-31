package controllers.loginControllers;

import controllers.Application;
import controllers.dashboardController.DashboardController;
import controllers.signUpFormControllers.FormsController;
import interfaces.Controlls;
import interfaces.Repository;
import interfaces.ViewsObjectGetter;
import models.users.AbstractUser;
import repository.LoginRepository;
import views.login.Login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginController implements Controlls<Login>, ViewsObjectGetter<Login> {

    private final Integer WIDTH = 420;

    private Login login;
    private Repository<LoginRepository> loginRep;
    private Application app;

    private LoginController(Application app) throws SQLException {
        loginRep = new LoginRepository();
        login = new Login();
        this.app = app;
        config();
        setSizes();
        build();
    }

    public static LoginController initLoginController(Application app) throws SQLException {
        return new LoginController(app);
    }

    @Override
    public void config() {
        JPanel mainPanel = login.getMainPanel();
        mainPanel.setLayout(new BorderLayout());
        generateListeners();
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.setVisible(true);
        login.repaint();
        login.validate();

    }

    @Override
    public void build() {
        JPanel mainPanel = login.getMainPanel();
        mainPanel.add(login.getLoginCredentials(), BorderLayout.CENTER);
        mainPanel.add(login.getLoginSignUpInvitation(), BorderLayout.SOUTH);
        login.add(mainPanel);


    }

    @Override
    public void setSizes() {
        JPanel c = login.getLoginCredentials();
        JPanel s = login.getLoginSignUpInvitation();
        c.setPreferredSize(new Dimension(WIDTH,250));
        s.setPreferredSize(new Dimension(WIDTH, 80));
        login.setSize(WIDTH,350);
    }

    private void generateListeners(){
        login.getLoginCredentials()
                .getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = login.getEmail().getInput().getText();
                char[] password = (login.getPassword().getInput().getPassword());

                try {
                    app.setUser(loginRep.login(email, new String(password)));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


                if(app.getUser() == null){
                    login.getErrorMessage().setText("email or password not correct!");
                    login.getErrorMessage().setBackground(Color.CYAN);
                    login.getErrorMessage().setOpaque(true);

                }else{
                    login.getErrorMessage().setText("");
                    login.getErrorMessage().setOpaque(false);
                    login.setVisible(false);
                    try {
                        redirectsToDashBoard(app.getUser());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        login.getLoginSignUpInvitation()
                .getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("auqui");
                    redirectsToForms();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }

    public AbstractUser getUser(){
        return app.getUser();
    }

    @Override
    public Login getViewObject() {
        return login;
    }

    private void redirectsToDashBoard(AbstractUser user) throws SQLException {
        app.setDashboard(DashboardController.initDashBoardController(user, app));
    }

    private void redirectsToForms() throws SQLException {
        login.dispose();
        app.setForms(FormsController.initFormsController(app));
        app.getForms().setVisible();
    }

    public void setVisible(){
        login.setVisible(true);
    }

    public void setVisible(Boolean flag){
        login.setVisible(flag);
    }

    public void setInvisible(){
        login.setVisible(false);
    }


}