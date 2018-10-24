package views.signUpForms;

import controllers.inputPanelController.InputPanelController;
import controllers.inputPanelController.InputPasswordController;
import views.inputPanel.InputPanel;
import views.inputPanel.InputPasswordPanel;

import javax.swing.*;

public class AdminSignUpForm extends JPanel {

    private JLabel formName;
    private InputPanel email;
    private InputPasswordPanel password;
    private JButton submit;
    private JButton cancel;

    public AdminSignUpForm() {
        formName = new JLabel("Admin signUp form");
        email = InputPanelController.initInputPanelController("Admin email: ").getViewObject();
        password = InputPasswordController.initInputPasswordController("Password: ").getViewObject();
        submit = new JButton("Submit");
        cancel = new JButton("Canel");
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

    public InputPasswordPanel getPassword() {
        return password;
    }

    public void setPassword(InputPasswordPanel password) {
        this.password = password;
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
