package views.forms;

import views.customComponents.ButtonPanel;
import views.customComponents.InputPanel;
import views.customComponents.MyCustomJFrame;
import views.customComponents.MyCustomJLabel;

import javax.swing.*;
import java.awt.*;

public class SignUp extends MyCustomJFrame {

    private final MyCustomJLabel titlePage;
    private final InputPanel email;
    private final InputPanel password;
    private final InputPanel confirmPassword;
    private final InputPanel phone;
    private final ButtonPanel submit;
    private final ButtonPanel cancel;

    public SignUp(String title) {
        super("Forms", 500,600);

        titlePage = new MyCustomJLabel(title, 30);
        email = new InputPanel("new email: ", new JTextField());
        password = new InputPanel("password: ", new JPasswordField());
        confirmPassword = new InputPanel("Confirm Password", new JPasswordField());
        phone = new InputPanel("Phone Number", new JTextField());
        submit = new ButtonPanel("Submit");
        cancel = new ButtonPanel("Cancel");
        getFramePanel().setLayout(new GridLayout(0,1));
        getFramePanel().add(this.titlePage.getLabel());
        getFramePanel().add(this.email);
        getFramePanel().add(password);
        getFramePanel().add(confirmPassword);
        getFramePanel().add(phone);

    }

    protected void addFormButtons(){
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1,2));
        bPanel.add(submit); bPanel.add(cancel); getFramePanel().add(bPanel);
    }

    protected void reduceElementsToEmailAndPassword(){
        getFramePanel().removeAll(); getFramePanel().add(this.email);getFramePanel().add(password);
        getFramePanel().add(this.confirmPassword); addFormButtons();
    }

    public JLabel getTitlePage() {
        return titlePage.getLabel();
    }

    public String getEmail() {
        return email.getInput().getInput().getText();
    }

    public String getPassword() {
        return password.getInput().getInput().getText();
    }

    public String getConfirmPassword() {
        return confirmPassword.getInput().getInput().getText();
    }

    public String getPhone() {
        return phone.getInput().getInput().getText();
    }

    public JButton getSubmit() {
        return submit.getButton().getButton();
    }

    public JButton getCancel() {
        return cancel.getButton().getButton();
    }
}
