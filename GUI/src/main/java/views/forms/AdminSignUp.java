package views.forms;

/**
 * This form will not be available for public. Only others admins have access to this form
 * this form is for registering another admins to the system and it will be done by an existing admin!!!
 */
public class AdminSignUp extends SignUp{
    public AdminSignUp() {
        super("Admin Sign Up");

        reduceElementsToEmailAndPassword();
        validadeAndRepaint();
    }

}
