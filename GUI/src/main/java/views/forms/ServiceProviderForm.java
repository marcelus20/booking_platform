package views.forms;

import static models.utils.ArrayListBuilder.arrayListBuilder;
import views.InputPanelContainer;
import views.customComponents.InputPanel;

import javax.swing.*;
import java.util.List;

public class ServiceProviderForm extends SignUp implements InputPanelContainer {

    private final InputPanel companyFullName;
    private final InputPanel firstLineAddress;
    private final InputPanel secondLineAddress;
    private final InputPanel city;
    private final InputPanel eircode;

    public ServiceProviderForm() {
        super("Service Provider Sign Up");
        companyFullName = new InputPanel("company full name: ", new JTextField());
        firstLineAddress = new InputPanel("First Line address: ", new JTextField());
        secondLineAddress = new InputPanel("Second Line Address: ", new JTextField());
        city = new InputPanel("city: ", new JTextField());
        eircode = new InputPanel("Eir Code: ", new JTextField());

        add(companyFullName);
        add(firstLineAddress);
        add(secondLineAddress);
        add(city);
        add(eircode);
        addFormButtons();
    }

    public String getCompanyFullName() {
        return companyFullName.getInput().getInput().getText().trim();
    }

    public String getFirstLineAddress() {
        return firstLineAddress.getInput().getInput().getText().trim();
    }

    public String getSecondLineAddress() {
        return secondLineAddress.getInput().getInput().getText().trim();
    }

    public String getCity() {
        return city.getInput().getInput().getText().trim();
    }

    public String getEircode() {
        return eircode.getInput().getInput().getText().trim();
    }

    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(super.getInputsPanel())
                .add(companyFullName).add(firstLineAddress).add(secondLineAddress)
                .add(city).add(eircode).build();
    }
}
