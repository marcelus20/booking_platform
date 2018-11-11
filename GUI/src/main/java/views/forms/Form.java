package views.forms;

import views.customComponents.MyCustomJFrame;

import javax.swing.*;


public class Form extends MyCustomJFrame{

    private final JMenuBar bar;
    private final JMenu menu;
    private final JMenuItem togleCustomerForm;
    private final JMenuItem toggleServiceForm;
    private SignUp registrationForm;


    public Form(SignUp registrationForm) {

        super("Sign Up", 600, 900);

        bar = new JMenuBar();
        menu = new JMenu("Registration Forms");
        togleCustomerForm = new JMenuItem("Sign up as a Customer");
        toggleServiceForm = new JMenuItem("Sign up as a Service Provider");
        this.registrationForm = registrationForm;
        menu.add(toggleServiceForm); menu.add(togleCustomerForm); bar.add(menu); addMenuBar(bar);

        getFramePanel().add(registrationForm);

        defaultClose();

        validadeAndRepaint();
    }

    public JMenuItem getTogleCustomerForm() {
        return togleCustomerForm;
    }

    public JMenuItem getToggleServiceForm() {
        return toggleServiceForm;
    }

    public SignUp getRegistrationForm() {
        return registrationForm;
    }

    public void withRegistrationForm(SignUp newForm){
        getFramePanel().removeAll();
        registrationForm = newForm;
        getFramePanel().add(newForm);
        validadeAndRepaint();
    }

}
