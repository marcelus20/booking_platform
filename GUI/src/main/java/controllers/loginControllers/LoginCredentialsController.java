package controllers.loginControllers;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.login.LoginCredentials;

import javax.swing.*;
import java.awt.*;

public class LoginCredentialsController implements Controlls, ViewsObjectGetter {
    private LoginCredentials loginCredentials;

    private LoginCredentialsController() {
        loginCredentials = new LoginCredentials();
        config();
        setSizes();
        build();
    }

    public static LoginCredentialsController makeLoginComponentsController(){
        return new LoginCredentialsController();
    }

    @Override
    public void config() {
        loginCredentials.setLayout(new BorderLayout());
    }

    @Override
    public void build() {
        JPanel wp = loginCredentials.getWelcomePanel(); JPanel tfp = loginCredentials.getGridTextFieldsPanel();
        tfp.add(loginCredentials.getEmail());
        tfp.add(loginCredentials.getPassword());
        tfp.add(loginCredentials.getErrorMessage()); tfp.add(loginCredentials.getLoginButton());
        wp.add(loginCredentials.getWelcomeLabel()); loginCredentials.add(wp, BorderLayout.NORTH);
        loginCredentials.add(tfp, BorderLayout.CENTER);
    }

    @Override
    public void setSizes() {
        JPanel wp = loginCredentials.getWelcomePanel();
        JPanel tfp = loginCredentials.getGridTextFieldsPanel();
        wp.setPreferredSize(new Dimension(420,50));
        tfp.setPreferredSize(new Dimension(200,100));

    }

    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }

    public void setLoginCredentials(LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }


    @Override
    public LoginCredentials getViewObject() {
        return loginCredentials;
    }
}
