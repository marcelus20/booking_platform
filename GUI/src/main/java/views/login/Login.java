package views.login;

import controllers.loginControllers.LoginController;
import controllers.loginControllers.LoginCredentialsController;
import controllers.loginControllers.LoginSignUpInvitationController;
import views.inputPanel.InputPanel;
import views.inputPanel.InputPasswordPanel;


import javax.swing.*;

public class Login extends JFrame{

    private JPanel mainPanel;

    private LoginCredentials loginCredentials;

    private LoginSignUpInvitation loginSignUpInvitation;

    public Login() {
        mainPanel = new JPanel();


        loginSignUpInvitation = LoginSignUpInvitationController
               .makeLoginSignUpInvitationController().getViewObject();
        loginCredentials = LoginCredentialsController
                .makeLoginComponentsController().getViewObject();
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

    public InputPanel getEmail(){
        return loginCredentials.getEmail();
    }
    public InputPasswordPanel getPassword(){
        return loginCredentials.getPassword();
    }

    public JLabel getErrorMessage(){
        return loginCredentials.getErrorMessage();
    }
}