package views.signUpForms;

import controllers.inputPanelController.InputPanelController;
import views.inputPanel.InputPanel;

import javax.swing.*;

public class ServiceProviderSignUpForm extends JPanel {

    private JLabel formName;
    private InputPanel companyFullName;
    private InputPanel email;
    private InputPanel password;
    private InputPanel phoneNumber;
    private JLabel statusForm;
    private JButton submit;
    private JButton cancel;





    public ServiceProviderSignUpForm() {
        formName = new JLabel("Service Provider Subscribing Form");
        companyFullName = InputPanelController.initInputPanelController("Company Full Name: ").getViewObject();
        email = InputPanelController.initInputPanelController("Email: ").getViewObject();
        password = InputPanelController.initInputPanelController("Password: ").getViewObject();
        phoneNumber = InputPanelController.initInputPanelController("Phone Number: ").getViewObject();
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

    public InputPanel getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(InputPanel companyFullName) {
        this.companyFullName = companyFullName;
    }

    public InputPanel getEmail() {
        return email;
    }

    public void setEmail(InputPanel email) {
        this.email = email;
    }

    public InputPanel getPassword() {
        return password;
    }

    public void setPassword(InputPanel password) {
        this.password = password;
    }

    public InputPanel getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(InputPanel phoneNumber) {
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
