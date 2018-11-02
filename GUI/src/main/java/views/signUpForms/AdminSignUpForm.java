package views.signUpForms;
import static controllers.controllers.inputPanelController.InputPanelController.initInputPanelController;


import views.inputPanel.InputPasswordPanel;
import views.inputPanel.TextFieldPanel;

import javax.swing.*;

public class AdminSignUpForm extends JPanel {

    private JLabel formName;
    private TextFieldPanel email;
    private InputPasswordPanel password;
    private JButton submit;
    private JButton cancel;

    public AdminSignUpForm() {
        formName = new JLabel("Admin signUp form");
        email = initInputPanelController(new TextFieldPanel("Admin email: ")).getViewObject();
        password = initInputPanelController(new InputPasswordPanel("Password: ")).getViewObject();
        submit = new JButton("Submit");
        cancel = new JButton("Canel");
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
