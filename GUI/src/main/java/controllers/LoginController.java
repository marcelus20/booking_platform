package controllers;

import controllers.dashboards.AdminDashboardController;
import controllers.dashboards.CustomerDashboardController;
import controllers.dashboards.ServiceDashBoardController;
import models.Database;
import models.enums.ServiceProviderStatus;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.*;
import models.utils.Tools;
import views.login.Login;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
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
                if(login.getMyCaptcha().captchaIsValid()){

                    AbstraticUser user = login(login.getEmail(), Tools.hashingPassword(login.getPassword()));
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
                            redirectToAdminDashboard(app);
                        }
                    }

                }else{
                    Tools.alertMsg(login, "You have entered the wrong captcha", "wrong captcha");
                    login.getMyCaptcha().getContent().removeAll();
                    login.getMyCaptcha().generateCaptcha();
                }

            }
        });
    }

    public AbstraticUser login(String email, String password){
        try {
            AbstraticUser user = null;

            String query  = new StringBuilder().append("SELECT * FROM ").append("users")
                    .append(" WHERE email = ").append("'").append(email).append("'")
                    .append(" AND password = '").append(password).append("' ;").toString();

            ResultSet rs;
            rs = Database.database().getStmt().executeQuery(query);
            while (rs.next()){
                if(UserType.valueOf(rs.getString("user_type")).equals(UserType.ADMIN)){
                    user = new Admin();
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.ADMIN);
                }else if (UserType.valueOf(rs.getString("user_type")).equals(UserType.SERVICE_PROVIDER)){
                    user = new ServiceProvider();
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.SERVICE_PROVIDER);
                }else if(UserType.valueOf(rs.getString("user_type")).equals(UserType.CUSTOMER)){
                    user = new Customer();
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.CUSTOMER);
                }
                user.withEmail(rs.getString("email"));
                user.withPassword(rs.getString("password"));
                user.withDateCreated(rs.getDate("date_created"));
            }
            if(user != null) {
                user = populateTheRestofAttributes(user);
                Log log = new Log(user.getId(), user.getEmail()+" has just logged in!");
                Tools.recordALogToDB(log);
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private AbstraticUser populateTheRestofAttributes(AbstraticUser user) throws SQLException {
        if(user.getUserType() == UserType.ADMIN){
            return user;
        }

        String query = "";
        if(user.getUserType().equals(UserType.CUSTOMER)){
            query = "SELECT * FROM customers WHERE c_id = '"+user.getId()+"'";
        }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
            query = "SELECT * FROM service_provider WHERE s_id = '"+user.getId()+"'";
        }


        ResultSet rs = Database.database().getStmt().executeQuery(query);
        while(rs.next()){
            if(user.getUserType().equals(UserType.CUSTOMER)){
                ((Customer) user).withFirstName(rs.getString("first_name"));
                ((Customer) user).withLastName(rs.getString("last_name"));
            }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
                ((ServiceProvider) user).withCompanyFullName(rs.getString("company_full_name"));
                ((ServiceProvider) user).withServiceProviderStatus(ServiceProviderStatus
                        .valueOf(rs.getString("approved_status")));
            }
        }

        return user;
    }

    private void redirectToAdminDashboard(Application app) {
        app.setAdminDashboardController(new AdminDashboardController(app));
    }

    private void redirectToCustomerDashboard(Application app){
        app.setCustomerDashboardController(new CustomerDashboardController(app));
    }

    private void redirectToServiceDashboard(Application app){
        app.setCustomerDashboardController(null);
        app.setServiceDashBoardController(new ServiceDashBoardController(app));
    }

    private void assignSignUpAButtonAFunction(){
        login.getSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.dispose();
                app.setFormController(new FormController(app));
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
