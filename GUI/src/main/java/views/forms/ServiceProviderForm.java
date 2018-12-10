package views.forms;

import static models.utils.ArrayListBuilder.arrayListBuilder;
import views.InputPanelContainer;
import views.customComponents.InputPanel;

import javax.swing.*;
import java.util.List;

/**
 * the service provider provider forms extends Signup  class and have few extra attributes for the location wise
 * Signup class implements InputPanelContainer so its method has been implemented in the botton of the class
 */
public class ServiceProviderForm extends SignUp{

    /**
     * List of attributes
     */
    private final InputPanel companyFullName;
    private final InputPanel firstLineAddress;
    private final InputPanel secondLineAddress;
    private final InputPanel city;
    private final InputPanel eircode;

    /**
     * CONSTRUCTOR
     */
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

    /**
     * getters
     * @return
     */
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

    /**
     * InputPanelController override method.
     * @return
     */
    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(super.getInputsPanel()) // calling super class method and joining with this class
                .add(companyFullName).add(firstLineAddress).add(secondLineAddress)
                .add(city).add(eircode).build();
    }
}
