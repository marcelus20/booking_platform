package views.signUpForms;
import static controllers.controllers.inputPanelController.InputPanelController.initInputPanelController;

import views.inputPanel.InputPasswordPanel;
import views.inputPanel.TextFieldPanel;

import javax.swing.*;

public class ServiceProviderSignUpForm extends JPanel {

    private JLabel formName;
    private TextFieldPanel companyFullName;
    private TextFieldPanel email;
    private InputPasswordPanel password;
    private TextFieldPanel phoneNumber;
    private JLabel statusForm;
    private JButton submit;
    private JButton cancel;





    public ServiceProviderSignUpForm() {
        formName = new JLabel("Service Provider Subscribing Form");
        companyFullName = initInputPanelController(new TextFieldPanel("Company Full Name: ")).getViewObject();
        email = initInputPanelController(new TextFieldPanel("Email: ")).getViewObject();
        password = initInputPanelController(new InputPasswordPanel("Password: ")).getViewObject();
        phoneNumber = initInputPanelController(new TextFieldPanel("Phone Number: ")).getViewObject();
        statusForm = new JLabel("");
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");

    }

    public JLabel getFormName() {
        return formName;
    }

    public void setFormName(JLabel formName) {
        this.formName = formName;
    }

    public TextFieldPanel getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(TextFieldPanel companyFullName) {
        this.companyFullName = companyFullName;
    }

    public TextFieldPanel getEmail() {
        return email;
    }

    public void setEmail(TextFieldPanel email) {
        this.email = email;
    }

    public InputPasswordPanel getPassword() {
        return password;
    }

    public void setPassword(InputPasswordPanel password) {
        this.password = password;
    }

    public TextFieldPanel getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(TextFieldPanel phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JLabel getStatusForm() {
        return statusForm;
    }

    public void setStatusForm(JLabel statusForm) {
        this.statusForm = statusForm;
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
