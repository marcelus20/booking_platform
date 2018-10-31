package views.login;

import controllers.inputPanelController.InputPanelController;
//import controllers.inputPanelController.InputPasswordController;
import utils.ArrayListBuilder;
import views.inputPanel.InputPanel;
import views.inputPanel.InputPasswordPanel;
import views.inputPanel.TextFieldPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginCredentials extends JPanel {

    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    private JPanel gridTextFieldsPanel;
    private InputPanel email;
    private InputPasswordPanel password;
    private JLabel errorMessage;
    private JButton loginButton;

    public LoginCredentials() {
        welcomePanel = new JPanel();
        welcomeLabel = new JLabel("Welcome to the booking Platform System!");
        gridTextFieldsPanel = new JPanel();
        email = InputPanelController.initInputPanelController(new TextFieldPanel("email: ")).getViewObject();
        password = InputPanelController.initInputPanelController(new InputPasswordPanel("password: ")).getViewObject();
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

    public InputPanel getEmail() {
        return email;
    }

    public void setEmail(InputPanel email) {
        this.email = email;
    }

    public InputPasswordPanel getPassword() {
        return password;
    }

    public void setPassword(InputPasswordPanel password) {
        this.password = password;
    }

    public List<Component> getAllPanels(){
        return new ArrayListBuilder().add(welcomePanel).build();
    }

}
