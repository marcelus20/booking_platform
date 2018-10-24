package views.signUpForms;

import controllers.inputPanelController.InputPanelController;
import controllers.inputPanelController.InputPasswordController;
import views.inputPanel.InputPanel;
import views.inputPanel.InputPasswordPanel;

import javax.swing.*;

public class CustomerSignUpForm extends JPanel {

    private JLabel formName;
    private InputPanel email;
    private InputPasswordPanel passwordPanel;
    private InputPanel firstName;
    private InputPanel lastName;
    private InputPanel phone;
    private JButton submit;
    private JButton cancel;

    public CustomerSignUpForm() {
        formName = new JLabel("Customer Subscribing Form");
        email = InputPanelController.initInputPanelController("Email: ").getViewObject();
        passwordPanel = InputPasswordController.initInputPasswordController("Password: ").getViewObject();
        firstName = InputPanelController.initInputPanelController("First Name: ").getViewObject();
        lastName = InputPanelController.initInputPanelController("Last Name: ").getViewObject();
        phone = InputPanelController.initInputPanelController("Last Name: ").getViewObject();
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public InputPanel getEmail() {
        return email;
    }

    public void setEmail(InputPanel email) {
        this.email = email;
    }

    public InputPasswordPanel getPasswordPanel() {
        return passwordPanel;
    }

    public void setPasswordPanel(InputPasswordPanel passwordPanel) {
        this.passwordPanel = passwordPanel;
    }

    public InputPanel getFirstName() {
        return firstName;
    }

    public void setFirstName(InputPanel firstName) {
        this.firstName = firstName;
    }

    public InputPanel getLastName() {
        return lastName;
    }

    public void setLastName(InputPanel lastName) {
        this.lastName = lastName;
    }

    public InputPanel getPhone() {
        return phone;
    }

    public void setPhone(InputPanel phone) {
        this.phone = phone;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JButton getCancel() {
        return cancel;
    }

    public void setCancel(JButton cancel) {
        this.cancel = cancel;
    }
}
