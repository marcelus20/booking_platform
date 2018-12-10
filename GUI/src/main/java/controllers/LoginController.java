package controllers;

import controllers.dashboards.AdminDashboardController;
import controllers.dashboards.CustomerDashboardController;
import controllers.dashboards.ServiceDashBoardController;
import models.tuples.entitiesRepresentation.*;
import models.utils.LoginModel;
import models.utils.Tools;
import views.login.Login;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;


/**
 * This class is responsible for populating the user session with the right user properties come from the db.
 * It will communicate with the database, check if there is any user with a specific credenttials (emaill and password)
 * if so, stabilishes a session (populates the user object from app) with the information about the user.
 */
public class LoginController implements Control{


    /**
     * Properties
     */
    private Login login;// the view login window
    private Application app;// the Application instance
    private List<Boolean> validator;// the array list of Booleans, for the form validation.


    /**
     * Contstrucor
     * @param app the object that has the user session to be ppopulated
     */
    public LoginController(Application app) {
        this.login = new Login();// initialise view window
        validator = Arrays.asList(false, false); // initialise the array of valid fields
        this.app = app;// populate app attribute with the outter app
        switchOffLoginButton(); // tun off login until validator has all true elements in list
        addButtonsAFunction();//override method from Control interface
        addInputsAListener();// same
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// shutdown when hit window exit
    }


    /**
     * Giving the button Login a function.
     * It will get information from the fields password and email and check in the database for
     * a result set with that credentials.
     */
    private void assignLoginButtonAFunction(){
        login.getLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(login.getMyCaptcha().captchaIsValid()){//if captcha is valid, proceed with the login algorithm

                    //connect to DB and check those credentials. Password is hashed.
                    //for that the Login model method login will do the work.
                    AbstraticUser user = new LoginModel().login(login.getEmail(), Tools.hashingPassword(login.getPassword()));
                    if(user == null){// if login() returns null, user does not exist
                        Tools.alertError(login, "Email or password not correct!", "Wrong Credentials");
                    }else{// else, go to the proper dasboard accordinly to the user type
                        login.dispose();//close log in window
                        app.setUser(user);// fill up the session variable
                        if(user instanceof Customer) {//if customer, go to customer dashboard
                            redirectToCustomerDashboard(app);
                        }else if (user instanceof ServiceProvider){// if service, fo to service dashboard
                            redirectToServiceDashboard(app);
                        }else{// if none of above and not null, this means it an admin. Go to adminDashboard
                            redirectToAdminDashboard(app);
                        }
                    }

                }else{// case captcha is not valid generate a new one and clear captcha textfield
                    Tools.alertMsg(login, "You have entered the wrong captcha", "wrong captcha");
                    login.getMyCaptcha().getContent().removeAll();
                    login.getMyCaptcha().generateCaptcha();
                    login.getMyCaptcha().clearTextField();
                }

            }
        });
    }


    /**
     * redirecting to Admin dashboard controller
     * @param app
     */

    private void redirectToAdminDashboard(Application app) {
        app.setAdminDashboardController(new AdminDashboardController(app));
    }

    /**
     * redirecting to Customer Controller
     * @param app
     */
    private void redirectToCustomerDashboard(Application app){
        app.setCustomerDashboardController(new CustomerDashboardController(app));
    }

    /**
     *redrecting to service provider controller
     * @param app
     */

    private void redirectToServiceDashboard(Application app){
        app.setCustomerDashboardController(null);
        app.setServiceDashBoardController(new ServiceDashBoardController(app));
    }

    /**
     * This function will give  listener to the sign up button located in login page.
     * When triggered, it will close login window and and lead user to the registration page
     */
    private void assignSignUpAButtonAFunction(){
        login.getSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.dispose();//close
                app.setFormController(new FormController(app));// calling login controller to mount view
            }
        });
    }

    /**
     * setting the view Login button not to enable
     */
    private void switchOffLoginButton(){
        login.getLogin().setEnabled(false);
    }

    /**
     * setting login button to back to enable
     */
    private void switchOnLoginButton(){
        login.getLogin().setEnabled(true);
    }

    /**
     * Method from Control interface. as login page has two buttons, here it is the functionality
     * assigning to the buttons
     */
    @Override
    public void addButtonsAFunction() {
        assignLoginButtonAFunction();
        assignSignUpAButtonAFunction();
    }

    /**
     * Also the override method from Control interface.
     * This method implementation is carrying out a listener for validating the form.
     * If the form is valid, it will call the switch on login button, else, the switchOffLoginButton.
     */

    @Override
    public void addInputsAListener() {
        login.getInputsPanel().forEach(input->{//listening to the change state of the field
            input.getInput().getInput().getDocument().addDocumentListener(new DocumentListener() {
                /**
                 * No matter which method is trigerred from this interface.
                 * It will all lead to the validate function that will carry out the switch of the button
                 * @param e
                 */
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        validate(e);//go to validate
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        validate(e); //go to validate
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    try {
                        validate(e); //go to validate
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                /**
                 * Validate the form itself. if email meets the requirements, it will set the validator field[0] to true.
                 * and same with password.
                 * At the end it will reduce the array of booleans to one boollean value indicating whether form is valid or not.
                 * The reduce operation will be carried over the && logical structure:
                 * Eg: true && true  = true
                 * whereas false && false = false
                 * @param e
                 * @throws Exception
                 */
                public void validate(DocumentEvent e) throws Exception {
                    Tools.formValidator(()->{//openning closure for implementing validator to this form
                        String inputTxt = input.getInput().getInput().getText();
                        String label = input.getLabel().getText();

                        /**
                         * the criteria used here is just a string bigger or smaller than 3 chars, cause this is
                         * not a registration page. it does not need to check if email is valid, if password has Uper lower
                         * symbol etc. So just by the length of the text should be enough for triggering the db data lookup
                         */
                        if(Tools.validateStringsNonSmallerEqualsThan3(inputTxt)){ // if text greater than 3 chars
                            if(label.contains("Email")){
                                validator.set(0, true); //set email to true
                            }else{
                                validator.set(1, true);// set password to true
                            }
                        }else{
                            if(label.contains("Email")){// if not, set them all to false
                                validator.set(0, false);
                            }else{
                                validator.set(1, false);
                            }
                        }

                        /**
                         * reducing the array of booleans to one unique boolean value which tells whether is
                         * valid or not.
                         */
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
