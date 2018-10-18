package views;

import controllers.LoginController;


import javax.swing.*;
import java.awt.*;

public class Login extends JFrame{

    private JLabel welcomeMessage;
    private JLabel labelUsername;
    private JTextField email;
    private JLabel labelPassword;
    private JPasswordField password;
    private JLabel errorMessage;
    private JPanel mainPanel;
    private JButton loginButton;
    private LoginController controller;

    public Login(LoginController controller) {

        welcomeMessage = new JLabel("Welcome to the booking Platform system");

         labelUsername = new JLabel("username: ");
         email = new JTextField(20);

         labelPassword = new JLabel("Password: ");
         password = new JPasswordField(20);

         errorMessage = new JLabel();

         loginButton = new JButton("Login");

         mainPanel = new JPanel();

         this.controller = controller;
         controller.addEventListener(loginButton);
    }

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(JLabel errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JLabel getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(JLabel welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public JLabel getLabelUsername() {
        return labelUsername;
    }

    public void setLabelUsername(JLabel labelUsername) {
        this.labelUsername = labelUsername;
    }

    public JTextField getEmail() {
        return email;
    }

    public void setEmail(JTextField email) {
        this.email = email;
    }

    public JLabel getLabelPassword() {
        return labelPassword;
    }

    public void setLabelPassword(JLabel labelPassword) {
        this.labelPassword = labelPassword;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public void setPassword(JPasswordField password) {
        this.password = password;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }
}