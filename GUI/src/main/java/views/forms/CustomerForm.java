package views.forms;

import static models.utils.ArrayListBuilder.arrayListBuilder;
import views.InputPanelContainer;
import views.customComponents.InputPanel;

import javax.swing.*;
import java.util.List;

public class CustomerForm extends SignUp implements InputPanelContainer {

    private final InputPanel firstName;
    private final InputPanel lastName;

    public CustomerForm() {
        super("Customer Sign Up");
        firstName = new InputPanel("First Name", new JTextField());
        lastName = new InputPanel("Second Name", new JTextField());

        add(firstName); add(lastName);
        addFormButtons();
    }

    public String getFirstName() {
        return firstName.getInput().getInput().getText().trim();
    }

    public String getLastName() {
        return lastName.getInput().getInput().getText().trim();
    }

    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(super.getInputsPanel()).add(firstName).add(lastName).build();
    }
}
