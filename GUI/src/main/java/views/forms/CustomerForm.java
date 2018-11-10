package views.forms;

import views.customComponents.InputPanel;

import javax.swing.*;

public class CustomerForm extends SignUp {

    private final InputPanel firstName;
    private final InputPanel secondName;

    public CustomerForm() {
        super("Customer Sign Up");
        firstName = new InputPanel("First Name", new JTextField());
        secondName = new InputPanel("Second Name", new JTextField());

        getFramePanel().add(firstName); getFramePanel().add(secondName);
        addFormButtons();
        validadeAndRepaint();
    }

    public String getFirstName() {
        return firstName.getInput().getInput().getText();
    }

    public String getSecondName() {
        return secondName.getInput().getInput().getText();
    }
}
