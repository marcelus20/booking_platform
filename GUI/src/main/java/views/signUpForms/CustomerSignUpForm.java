package views.signUpForms;

import controllers.controllers.inputPanelController.InputPanelController;
//import controllers.controllers.inputPanelController.InputPasswordController;
import views.inputPanel.InputPasswordPanel;
import views.inputPanel.TextFieldPanel;

import javax.swing.*;

public class CustomerSignUpForm extends JPanel {

    private JLabel formName;
    private TextFieldPanel email;
    private InputPasswordPanel passwordPanel;
    private TextFieldPanel firstName;
    private TextFieldPanel lastName;
    private TextFieldPanel phone;
    private JButton submit;
    private JButton cancel;

    public CustomerSignUpForm() {
        formName = new JLabel("Customer Subscribing Form");
        email = InputPanelController.initInputPanelController(new TextFieldPanel("email")).getViewObject();
        passwordPanel = InputPanelController.initInputPanelController(new InputPasswordPanel("password")).getViewObject();
        firstName = InputPanelController.initInputPanelController(new TextFieldPanel("First Name")).getViewObject();
        lastName = InputPanelController.initInputPanelController(new TextFieldPanel("Last Name: ")).getViewObject();
        phone = InputPanelController.initInputPanelController(new TextFieldPanel("Phone Number: ")).getViewObject();
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public TextFieldPanel getEmail() {
        return email;
    }

    public void setEmail(TextFieldPanel email) {
        this.email = email;
    }

    public InputPasswordPanel getPasswordPanel() {
        return passwordPanel;
    }

    public void setPasswordPanel(InputPasswordPanel passwordPanel) {
        this.passwordPanel = passwordPanel;
    }

    public TextFieldPanel getFirstName() {
        return firstName;
    }

    public void setFirstName(TextFieldPanel firstName) {
        this.firstName = firstName;
    }

    public TextFieldPanel getLastName() {
        return lastName;
    }

    public void setLastName(TextFieldPanel lastName) {
        this.lastName = lastName;
    }

    public TextFieldPanel getPhone() {
        return phone;
    }

    public void setPhone(TextFieldPanel phone) {
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
