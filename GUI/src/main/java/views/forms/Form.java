/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.forms;

import views.customComponents.MyCustomJFrame;
import javax.swing.*;

/**
 * This class is the container that will hold the Customer sign in form and service provider sign in form
 *
 * User will be able to change to one of the forms he likes by toggling customer or service form
 */
public class Form extends MyCustomJFrame{
    /**
     * List of attributes
     */
    private final JMenuBar bar; // menu bar
    private final JMenu menu; // menu
    private final JMenuItem togleCustomerForm; // menu item to toggle customer form
    private final JMenuItem toggleServiceForm; // menu item to toggle service form
    private SignUp registrationForm; // The signup form that can be either customer or service provider form


    /**
     * CONSTRUCTOR
     * @param registrationForm
     */
    public Form(SignUp registrationForm) {
        super("Sign Up", 600, 900);
        bar = new JMenuBar();
        menu = new JMenu("Click here to change Form");
        togleCustomerForm = new JMenuItem("Sign up as a Customer");
        toggleServiceForm = new JMenuItem("Sign up as a Service Provider");
        this.registrationForm = registrationForm;
        menu.add(toggleServiceForm); menu.add(togleCustomerForm); bar.add(menu); addMenuBar(bar);
        getFramePanel().add(registrationForm);
        defaultClose();
        validadeAndRepaint();
    }

    /**
     * GETTERS
     * @return
     */

    public JMenuItem getTogleCustomerForm() {
        return togleCustomerForm;
    }

    public JMenuItem getToggleServiceForm() {
        return toggleServiceForm;
    }

    public SignUp getRegistrationForm() {
        return registrationForm;
    }

    /**
     * SETTER
     * @param newForm
     */
    public void withRegistrationForm(SignUp newForm){
        getFramePanel().removeAll();
        registrationForm = newForm;
        getFramePanel().add(newForm);
        validadeAndRepaint();
    }

}
