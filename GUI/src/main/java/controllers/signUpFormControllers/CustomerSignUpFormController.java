package controllers.signUpFormControllers;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.signUpForms.CustomerSignUpForm;

import javax.swing.*;
import java.awt.*;


public class CustomerSignUpFormController implements Controlls, ViewsObjectGetter {

    private CustomerSignUpForm customerSignUpForm;

    public CustomerSignUpFormController() {
        customerSignUpForm = new CustomerSignUpForm();
        config();
        setSizes();
        build();
    }
    public static CustomerSignUpFormController initCustomerSignUpController(){
        return new CustomerSignUpFormController();
    }


    @Override
    public void config() {

    }

    @Override
    public void build() {
        customerSignUpForm.add(customerSignUpForm.getFormName());
        customerSignUpForm.add(customerSignUpForm.getFirstName());
        customerSignUpForm.add(customerSignUpForm.getLastName());
        customerSignUpForm.add(customerSignUpForm.getEmail());
        customerSignUpForm.add(customerSignUpForm.getPasswordPanel());
        customerSignUpForm.add(customerSignUpForm.getPhone());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(customerSignUpForm.getSubmit(), BorderLayout.LINE_START);
        buttonsPanel.add(customerSignUpForm.getCancel(), BorderLayout.LINE_END);
        customerSignUpForm.add(buttonsPanel);

    }

    @Override
    public void setSizes() {
        customerSignUpForm.getFormName().setFont(new Font("Courier", Font.BOLD,30));
    }

    @Override
    public CustomerSignUpForm getViewObject() {
        return customerSignUpForm;
    }
}
