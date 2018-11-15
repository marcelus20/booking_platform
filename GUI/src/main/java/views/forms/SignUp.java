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

public class SignUp extends JPanel implements InputPanelContainer {

    private final MyCustomJLabel titlePage;
    protected final InputPanel email;
    protected final InputPanel password;
    protected final InputPanel confirmPassword;
    private final InputPanel phone;
    private final ButtonPanel submit;
    private final ButtonPanel cancel;

    public SignUp(String title) {
//        super("Forms", 500,600);

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

    protected void addFormButtons(){
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1,2));
        bPanel.add(submit); bPanel.add(cancel); add(bPanel);
    }

    protected void reduceElementsToEmailAndPassword(){
        removeAll(); add(this.email); add(password);
        add(this.confirmPassword); addFormButtons();
    }

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

    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(new ArrayList<InputPanel>())
                .add(email).add(password).add(confirmPassword).add(phone).build();
    }
}
