package views.forms;

import views.InputPanelContainer;
import views.customComponents.ButtonPanel;
import views.customComponents.InputPanel;

import views.customComponents.MyCustomJLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static models.utils.ArrayListBuilder.arrayListBuilder;

/**
 * This class is the foundation for the Customer and Service providers registration forms.
 *
 * This class contains the inputs that are common bettween customer and service provider.
 *
 * It also implements the inputPanelContainer cause the form is a collection of inputs that
 * should be configured by a controller. The interface implemented method helps by returning these inputs
 * in an array for the controllers to loop through
 */
public class SignUp extends JPanel implements InputPanelContainer {

    /**
     * LIST OF ATTRIBUTES - all inputs along with theirlables
     */
    private final MyCustomJLabel titlePage;
    protected final InputPanel email;
    protected final InputPanel password;
    protected final InputPanel confirmPassword;
    private final InputPanel phone;
    private final ButtonPanel submit;
    private final ButtonPanel cancel;

    /**
     * CONSTRUCTOR
     * @param title
     */
    public SignUp(String title) {

        titlePage = new MyCustomJLabel(title, 30);
        email = new InputPanel("new email: ", new JTextField());
        password = new InputPanel("password: ", new JPasswordField());
        confirmPassword = new InputPanel("Confirm Password", new JPasswordField());
        phone = new InputPanel("Phone Number", new JTextField());
        submit = new ButtonPanel("Submit");
        cancel = new ButtonPanel("Cancel");
        setLayout(new GridLayout(0,1));
        add(this.titlePage.getLabel());
        add(this.email);
        add(password);
        add(confirmPassword);
        add(phone);
    }

    /**
     * add buttons later on to panel, after the subclasses have included their own inputs panel  to form
     */
    protected void addFormButtons(){
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1,2));
        bPanel.add(submit); bPanel.add(cancel); add(bPanel);
    }

    /**
     * THIS METHOD IS TO BE USED FOR THE CREATION OF THE ADMIN FORM SIGNUP.
     * As admin only needs email, password and confirm password and button.
     * this method return makes this class have just this buttons
     */
    protected void reduceElementsToEmailAndPassword(){
        removeAll(); add(this.email); add(password);
        add(this.confirmPassword); addFormButtons();
    }

    /**
     * getters
     * @return
     */
    public JLabel getTitlePage() {
        return titlePage.getLabel();
    }

    public String getEmail() {
        return email.getInput().getInput().getText().trim();
    }

    public String getPassword() {
        return password.getInput().getInput().getText().trim();
    }

    public String getConfirmPassword() {
        return confirmPassword.getInput().getInput().getText().trim();
    }

    public String getPhone() {
        return phone.getInput().getInput().getText().trim();
    }

    public JButton getSubmit() {
        return submit.getButton().getButton();
    }

    public JButton getCancel() {
        return cancel.getButton().getButton();
    }

    /**
     * method override implementation from inputPanelContainer interface
     * @return
     */
    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(new ArrayList<InputPanel>())
                .add(email).add(password).add(confirmPassword).add(phone).build();
    }
}
