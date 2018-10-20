package views.login;

import utils.ArrayListBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginCredentials extends JPanel {

    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    private JPanel gridTextFieldsPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField email;
    private JPasswordField password;
    private JLabel errorMessage;
    private JButton loginButton;

    public LoginCredentials(JPanel welcomePanel, JLabel welcomeLabel, JPanel gridTextFieldsPanel, JLabel emailLabel, JLabel passwordLabel, JTextField email, JPasswordField password, JButton loginButton) {
        this.welcomePanel = welcomePanel;
        this.welcomeLabel = welcomeLabel;
        this.gridTextFieldsPanel = gridTextFieldsPanel;
        this.emailLabel = emailLabel;
        this.passwordLabel = passwordLabel;
        this.email = email;
        this.password = password;
        this.errorMessage = new JLabel("");
        this.loginButton = loginButton;
    }

    public LoginCredentials() {
        welcomePanel = new JPanel();
        welcomeLabel = new JLabel("Welcome to the booking Platform System!");
        gridTextFieldsPanel = new JPanel();
        emailLabel = new JLabel("email: ");
        passwordLabel = new JLabel("password: ");
        email = new JTextField(20);
        password = new JPasswordField(20);
        errorMessage = new JLabel();
        loginButton = new JButton("Login");

    }

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(JLabel errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JPanel getWelcomePanel() {
        return welcomePanel;
    }

    public void setWelcomePanel(JPanel welcomePanel) {
        this.welcomePanel = welcomePanel;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public void setWelcomeLabel(JLabel welcomeLabel) {
        this.welcomeLabel = welcomeLabel;
    }

    public JPanel getGridTextFieldsPanel() {
        return gridTextFieldsPanel;
    }

    public void setGridTextFieldsPanel(JPanel gridTextFieldsPanel) {
        this.gridTextFieldsPanel = gridTextFieldsPanel;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JTextField getEmail() {
        return email;
    }

    public void setEmail(JTextField email) {
        this.email = email;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public void setPassword(JPasswordField password) {
        this.password = password;
    }

    public List<Component> getAllPanels(){
        return new ArrayListBuilder().add(welcomePanel).build();
    }

}
