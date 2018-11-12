package controllers;

import controllers.dashboards.CustomerDashboardController;
import controllers.dashboards.ServiceDashBoardController;
import models.Database;
import models.entitiesRepresentation.Customer;
import models.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;
import models.users.AbstraticUser;
import views.login.Login;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class LoginController implements Control{

    private Login login;
    private Application app;
    private List<Boolean> validator;

    public LoginController(Application app) {
        this.login = new Login();
        validator = Arrays.asList(false, false);
        this.app = app;
        switchOffLoginButton();
        addButtonsAFunction();
        addInputsAListener();
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private void assignLoginButtonAFunction(){
        login.getLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database db = new Database();
                    AbstraticUser user = db.login(login.getEmail(), Tools.hashingPassword(login.getPassword()));
                    if(user == null){
                        Tools.alertError(login, "Email or password not correct!", "Wrong Credentials");
                    }else{
                        login.dispose();
                        app.setUser(user);
                        if(user instanceof Customer) {
                            redirectToCustomerDashboard(app);
                        }else if (user instanceof ServiceProvider){
                            redirectToServiceDashboard(app);
                        }else{
                            //DO NOTHING FOR NOW
                        }
                    }
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void redirectToCustomerDashboard(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        app.setCustomerDashboardController(new CustomerDashboardController(app));
    }

    private void redirectToServiceDashboard(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        app.setCustomerDashboardController(null);
        app.setServiceDashBoardController(new ServiceDashBoardController(app));
    }

    private void assignSignUpAButtonAFunction(){
        login.getSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.dispose();
                try {
                    app.setFormController(new FormController(app));
                } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void switchOffLoginButton(){
        login.getLogin().setEnabled(false);
    }

    private void switchOnLoginButton(){
        login.getLogin().setEnabled(true);
    }

    @Override
    public void addButtonsAFunction() {
        assignLoginButtonAFunction();
        assignSignUpAButtonAFunction();
    }

    @Override
    public void addInputsAListener() {
        login.getInputsPanel().forEach(input->{
            input.getInput().getInput().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        validate(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        validate(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    try {
                        validate(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                public void validate(DocumentEvent e) throws Exception {
                    Tools.formValidator(()->{
                        String inputTxt = input.getInput().getInput().getText();
                        String label = input.getLabel().getText();

                        if(Tools.validateStringsNonSmallerEqualsThan3(inputTxt)){
                            if(label.contains("Email")){
                                validator.set(0, true);
                            }else{
                                validator.set(1, true);
                            }
                        }else{
                            if(label.contains("Email")){
                                validator.set(0, false);
                            }else{
                                validator.set(1, false);
                            }
                        }
                        if(validator.stream().reduce(true, (current, next)->current && next)){
                            switchOnLoginButton();
                        }else{
                            switchOffLoginButton();
                        }
                        return null;
                    });
                }
            });
        });
    }
}
