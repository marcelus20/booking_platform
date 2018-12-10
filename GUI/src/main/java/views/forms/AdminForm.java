/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
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

    /**
     * SignUp superclass implements InputPanel container, therefore, this method should be implemented there.
     * @return the array of inputs conatined in the adimin form.
     */
    @Override
    public List<InputPanel> getInputsPanel() {
        return new ArrayListBuilder<InputPanel>().add(email).add(password).add(confirmPassword).build();
    }
}
