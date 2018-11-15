package views.forms;

import models.utils.ArrayListBuilder;
import views.customComponents.InputPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * This form will not be available for public. Only others admins have access to this form
 * this form is for registering another admins to the system and it will be done by an existing admin!!!
 */
public class AdminForm extends SignUp{
    public AdminForm() {
        super("Admin Sign Up");

        reduceElementsToEmailAndPassword();

    }

    @Override
    public List<InputPanel> getInputsPanel() {
        return new ArrayListBuilder<InputPanel>().add(email).add(password).add(confirmPassword).build();
    }
}
