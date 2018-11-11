package controllers;

import models.Database;
import models.entitiesRepresentation.Customer;
import models.entitiesRepresentation.ServiceProvider;
import models.repositories.CustomerRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.utils.Tools;
import views.forms.CustomerForm;
import views.forms.Form;
import views.forms.ServiceProviderForm;

import javax.xml.ws.Service;

import static models.tuples.Tuple.tuple;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;


public class FormController implements Control{

    private Tuple<ServiceProviderForm, List<Boolean>> serviceProviderForm;
    private Tuple<CustomerForm, List<Boolean>> customerForm;
    private Form signUpForm;
    private Application app;
    private List<Boolean> validator;
    private Repository<ServiceProviderRepository> sRep;
    private Repository<CustomerRepository> cRep;

    public FormController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        CustomerForm cf = new CustomerForm();
        ServiceProviderForm svf = new ServiceProviderForm();

        customerForm = tuple(cf, cf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));
        serviceProviderForm = tuple(svf, svf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));

        signUpForm = new Form(customerForm.getX());
        this.app = app;
        validator = customerForm.getY();

        sRep = new ServiceProviderRepository();
        cRep = new CustomerRepository();



        addButtonsAFunction();
        addInputsAListener();

    }

    public Form getSignUpForm() {
        return signUpForm;
    }


    private void validatePassword(String pass, String confirmPass){
        Boolean passFlag = Arrays.asList(Tools.validatePasswordsInput(pass, confirmPass), Tools.validatePasswordCriteria(confirmPass))
                .stream().reduce(true,((acc, next)->acc && next));
        if(passFlag){
            validator.set(1,true); validator.set(2,true);
        }else{
            validator.set(1, false); validator.set(2, false);
        }
    }

    private void validateEmail(String email){
        validator.set(0, Tools.validateEmail(email));
    }

    private void validatePhone(String phone){
        if(Tools.validateStringsNonSmallerEqualsThan3(phone)) {
            validator.set(3, true);
        }else {
            validator.set(3, false);
        }
    }

    private void validateCompanyFullName(String companyName){
        if(Tools.validateNotNumericStrings(companyName)) {
            validator.set(4, true);
        }else {
            validator.set(4, false);
        }
    }

    private String generateFormErrorMessage() {
        return new StringBuilder().append("The form is not valid, please follow the rules bellow:\n")
                .append(" * All fields must not be blank\n")
                .append(" * password must have between 8 and 20 length with at least \n")
                .append(" 1 Strange Symbol (@#$%), 1 Capital letter, 1 lower case and a number")
                .append(" * email field must be a valid email address").toString();
    }

    private String generateWarningMessage(){
        return new StringBuilder().append("You have just subscribed to the Booking Platform System\n")
                .append("Thank you very much!\n")
                .append("The next Step now is to wait one of the administrator to approve your subscription\n")
                .append("Bear in mind that your subscription request can be refused if your details do not seem to be genuine").toString();
    }

    private void validateLastName(String lastName) {
        if(Tools.validateNotNumericStrings(lastName)){
            validator.set(5, true);
        }else{
            validator.set(5, false);
        }
    }

    private void validateFirstName(String firstName) {
        if(Tools.validateNotNumericStrings(firstName)){
            validator.set(4, true);
        }else{
            validator.set(4, false);
        }
    }

    @Override
    public void addButtonsAFunction() {
        CustomerForm cf = customerForm.getX();
        ServiceProviderForm svf = serviceProviderForm.getX();

        Arrays.asList(cf.getSubmit(), svf.getSubmit()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Tools.formValidator(()->{
                            if(signUpForm.getRegistrationForm() instanceof CustomerForm){
                                validatePassword(cf.getPassword(), cf.getConfirmPassword());
                                validateEmail(cf.getEmail()); validatePhone(cf.getPhone());
                                validateFirstName(cf.getFirstName());
                                validateLastName(cf.getLastName());


                            }else if (signUpForm.getRegistrationForm() instanceof ServiceProviderForm){
                                validatePassword(svf.getPassword(), svf.getConfirmPassword());
                                validateEmail(svf.getEmail()); validatePhone(svf.getPhone());
                                validateCompanyFullName(svf.getCompanyFullName());


                            }
                            return null;
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if(validator.stream().reduce(true, (acc, next)->acc && next)){
                        if(signUpForm.getRegistrationForm() instanceof ServiceProviderForm){
                            Tools.alertWarning(signUpForm, generateWarningMessage(), "Your status is pendent");
                            ServiceProvider service = new ServiceProvider();
                            service.withPassword(svf.getPassword()); service.withEmail(svf.getEmail());
                            service.withDateCreated(new Date(System.currentTimeMillis()));
                            service.withPhone(svf.getPhone()); service.withCompanyFullName(svf.getCompanyFullName());
                            service.withApprovedStatus("pendent");
                            try {
                                sRep.addToDB(service);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                            redirectToLogin();

                        }else{
                            Tools.alertMsg(signUpForm, "You have just subscribed! You will be redirected to Login Page", "Success");
                            Customer customer = new Customer();
                            customer.withPassword(cf.getPassword()); customer.withEmail(cf.getEmail());
                            customer.withPhone(cf.getPhone()); customer.withFirstName(cf.getFirstName());
                            customer.withLastName(cf.getLastName());
                            customer.withDateCreated(new Date(System.currentTimeMillis()));
                            try {
                                cRep.addToDB(customer);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            redirectToLogin();
                        }

                    }else{
                        String msg = generateFormErrorMessage();
                        Tools.alertError(signUpForm, msg, "Form not valid");
                    }
                }
            });
        });

        Arrays.asList(customerForm.getX().getCancel(), serviceProviderForm.getX().getCancel()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    redirectToLogin();
                }
            });
        });
    }

    private void redirectToLogin() {
        signUpForm.dispose();
        app.setFormController(null);
        app.login();
    }

    @Override
    public void addInputsAListener() {


        signUpForm.getRegistrationForm().getInputsPanel().get(5);

        signUpForm.getToggleServiceForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpForm.withRegistrationForm(serviceProviderForm.getX());

                validator = serviceProviderForm.getY();

            }
        });

        signUpForm.getTogleCustomerForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                signUpForm.withRegistrationForm(customerForm.getX());

                validator = customerForm.getY();

            }
        });

    }



}
