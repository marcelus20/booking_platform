package views.signUpForms;

import static controllers.controllers.signUpFormControllers
        .CustomerSignUpFormController.initCustomerSignUpController;
import static controllers.controllers.signUpFormControllers
        .ServiceProviderSignUpFormController.initServiceProviderSignUpFormController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class Forms extends JFrame {

    private CustomerSignUpForm customerSignUpForm;
    private ServiceProviderSignUpForm serviceProviderSignUpForm;
    private JPanel sideBar;
    private JPanel formView;
    private JButton toggleCustomerButton;
    private JButton toggleServiceProviderButton;


    public Forms() throws HeadlessException, SQLException {
        customerSignUpForm = initCustomerSignUpController().getViewObject();
        serviceProviderSignUpForm = initServiceProviderSignUpFormController().getViewObject();
        sideBar = new JPanel();
        formView = new JPanel();
        toggleCustomerButton = new JButton("Sign up as a Costumer");
        toggleServiceProviderButton = new JButton("Sign up as a Service Provider");
    }

    public CustomerSignUpForm getCustomerSignUpForm() {
        return customerSignUpForm;
    }

    public ServiceProviderSignUpForm getServiceProviderSignUpForm() {
        return serviceProviderSignUpForm;
    }

    public JPanel getSideBar() {
        return sideBar;
    }

    public JPanel getFormView() {
        return formView;
    }

    public JButton getToggleCustomerButton() {
        return toggleCustomerButton;
    }

    public JButton getToggleServiceProviderButton() {
        return toggleServiceProviderButton;
    }
}