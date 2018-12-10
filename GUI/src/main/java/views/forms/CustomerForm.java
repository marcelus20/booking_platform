/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.forms;

import static models.utils.ArrayListBuilder.arrayListBuilder;
import views.InputPanelContainer;
import views.customComponents.InputPanel;

import javax.swing.*;
import java.util.List;

/**
 * This is customer form. Because it extends Signup, it will get all of the others inputs for the user
 * table wise and it will have these two more inputs added (first name and last name)
 */
public class CustomerForm extends SignUp{

    /**
     * List of attributes - the inputs
     */
    private final InputPanel firstName;
    private final InputPanel lastName;

    /**
     * constructor
     */
    public CustomerForm() {
        super("Customer Sign Up");
        firstName = new InputPanel("First Name", new JTextField());
        lastName = new InputPanel("Second Name", new JTextField());

        add(firstName); add(lastName);
        addFormButtons();
    }

    /**
     * getter
     * @return
     */
    public String getFirstName() {
        return firstName.getInput().getInput().getText().trim();
    }

    public String getLastName() {
        return lastName.getInput().getInput().getText().trim();
    }

    /**
     * override implementation from the interface InputPanelContainer (SignUp class implements)
     * @return the array of inputs
     */
    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(super.getInputsPanel()).add(firstName).add(lastName).build();
    }
}
