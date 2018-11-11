package views.forms;

import static models.utils.ArrayListBuilder.arrayListBuilder;
import views.InputPanelContainer;
import views.customComponents.InputPanel;

import javax.swing.*;
import java.util.List;

public class ServiceProviderForm extends SignUp implements InputPanelContainer {

    private final InputPanel companyFullName;

    public ServiceProviderForm() {
        super("Service Provider Sign Up");
        companyFullName = new InputPanel("company full name", new JTextField());

        add(companyFullName);
        addFormButtons();
    }

    public String getCompanyFullName() {
        return companyFullName.getInput().getInput().getText();
    }

    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(super.getInputsPanel()).add(companyFullName).build();
    }
}
