package controllers.controllers.signUpFormControllers;

import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import models.entities.Customer;
import models.repositories.CustomerRepository;
import views.signUpForms.CustomerSignUpForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class CustomerSignUpFormController implements Controlls, ViewsObjectGetter {

    private CustomerSignUpForm customerSignUpForm;
    private Customer customer;
    private CustomerRepository csr;


    public CustomerSignUpFormController() throws SQLException {
        customerSignUpForm = new CustomerSignUpForm();
        customer = new Customer();
        csr = new CustomerRepository();
        config();
        setSizes();
        build();
    }
    public static CustomerSignUpFormController initCustomerSignUpController() throws SQLException {
        return new CustomerSignUpFormController();
    }


    @Override
    public void config() {

        assignButtonsAFunction();
        customerSignUpForm.setLayout(new GridLayout(0,1));
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

    private void assignButtonsAFunction(){
        customerSignUpForm.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer.withFirstName(customerSignUpForm.getFirstName().getInput().getText());
                customer.withLastName(customerSignUpForm.getLastName().getInput().getText());
                customer.withEmail(customerSignUpForm.getEmail().getInput().getText());
                customer.withPassword(new String(customerSignUpForm.getPasswordPanel().getInput().getPassword()));
                customer.withPhone(customerSignUpForm.getPhone().getInput().getText());
                System.out.println(customer);
                csr.insertData(customer);
            }
        });

    }
}
