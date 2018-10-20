package views.login;

import controllers.loginControllers.LoginController;
import controllers.loginControllers.LoginCredentialsController;
import controllers.loginControllers.LoginSignUpInvitationController;
import interfaces.Controlls;


import javax.swing.*;

public class Login extends JFrame{
    /*
    private JLabel welcomeMessage;
    private JLabel labelUsername;
    private JTextField email;
    private JLabel labelPassword;
    private JPasswordField password;
    private JLabel errorMessage;
    private JPanel mainPanel;
    private JPanel signUpPanel;
    private JButton loginButton;
    private JLabel signUpLabel;
    private JButton signUpButton;
    private LoginController controller;
    */
    private JPanel mainPanel;

    private LoginCredentials loginCredentials;



    private LoginSignUpInvitation loginSignUpInvitation;

    public Login(LoginController controller) {
        mainPanel = new JPanel();


        loginSignUpInvitation = LoginSignUpInvitationController
               .makeLoginSignUpInvitationController().buildLoginSignUpInvitation();
        loginCredentials = LoginCredentialsController
                .makeLoginComponentsController().buildLoginComponentView();
        //this.add(loginCredentials);
        controller.addEventListener(loginCredentials.getLoginButton());

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }

    public void setLoginCredentials(LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }

    public LoginSignUpInvitation getLoginSignUpInvitation() {
        return loginSignUpInvitation;
    }

    public void setLoginSignUpInvitation(LoginSignUpInvitation loginSignUpInvitation) {
        this.loginSignUpInvitation = loginSignUpInvitation;
    }

    public JTextField getEmail(){
        return loginCredentials.getEmail();
    }
    public JPasswordField getPassword(){
        return loginCredentials.getPassword();
    }

    public JLabel getErrorMessage(){
        return loginCredentials.getErrorMessage();
    }
}