package views.forms;

import views.customComponents.InputPanel;

import javax.swing.*;

public class ServiceProviderForm extends SignUp {

    private final InputPanel companyFullName;

    public ServiceProviderForm() {
        super("Service Provider Sign Up");
        companyFullName = new InputPanel("company full name", new JTextField());

        getFramePanel().add(companyFullName);
        addFormButtons();
        validadeAndRepaint();
    }

    public String getCompanyFullName() {
        return companyFullName.getInput().getInput().getText();
    }
}
