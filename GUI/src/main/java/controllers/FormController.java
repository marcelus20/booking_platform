package controllers;

import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.Customer;
import models.tuples.entitiesRepresentation.Location;
import models.tuples.entitiesRepresentation.Phone;
import models.tuples.entitiesRepresentation.ServiceProvider;
import models.repositories.CustomerRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.utils.Tools;
import views.forms.CustomerForm;
import views.forms.Form;
import views.forms.ServiceProviderForm;
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
    private Form form;
    private Application app;
    private List<Boolean> validator;
    private Repository<ServiceProvider> sRep;
    private Repository<Customer> cRep;

    public FormController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        CustomerForm cf = new CustomerForm();
        ServiceProviderForm svf = new ServiceProviderForm();

        customerForm = tuple(cf, cf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));
        serviceProviderForm = tuple(svf, svf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));

        form = new Form(customerForm.get_1());
        this.app = app;
        validator = customerForm.get_2();

        sRep = new ServiceProviderRepository();
        cRep = new CustomerRepository();



        addButtonsAFunction();
        addInputsAListener();

    }

    public Form getForm() {
        return form;
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

    public void validateLocation(){

        Boolean firstAndSecondLineAddress = Arrays.asList(Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(5).getInput().getInput().getText().trim()), Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(6).getInput().getInput().getText().trim()))
                .stream().reduce((acc, next )->acc || next).get();

        if(firstAndSecondLineAddress){
            validator.set(5, true);  validator.set(6, true);
        }else{
            validator.set(5, false); validator.set(6, false);
        }
        if(Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(7).getInput().getInput().getText().trim())){
            validator.set(7, true);
        }else{
            validator.set(7, false);
        }
        if(serviceProviderForm.get_1()
                .getInputsPanel().get(8).getInput().getInput().getText().trim().length() > 1){
            validator.set(8, true);
        }else{
            validator.set(8, false);
        }

    }

    @Override
    public void addButtonsAFunction() {
        CustomerForm cf = customerForm.get_1();
        ServiceProviderForm svf = serviceProviderForm.get_1();

        Arrays.asList(cf.getSubmit(), svf.getSubmit()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Tools.formValidator(()->{
                            if(form.getRegistrationForm() instanceof CustomerForm){
                                validatePassword(cf.getPassword(), cf.getConfirmPassword());
                                validateEmail(cf.getEmail()); validatePhone(cf.getPhone());
                                validateFirstName(cf.getFirstName());
                                validateLastName(cf.getLastName());


                            }else if (form.getRegistrationForm() instanceof ServiceProviderForm){
                                validatePassword(svf.getPassword(), svf.getConfirmPassword());
                                validateEmail(svf.getEmail()); validatePhone(svf.getPhone());
                                validateCompanyFullName(svf.getCompanyFullName());
                                validateLocation();


                            }
                            return null;
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if(validator.stream().reduce(true, (acc, next)->acc && next)){
                        if(form.getRegistrationForm() instanceof ServiceProviderForm){
                            Tools.alertWarning(form, generateWarningMessage(), "Your status is pendent");

                            ServiceProvider service = new ServiceProvider();
                            service.withPassword(svf.getPassword()); service.withEmail(svf.getEmail());
                            service.withDateCreated(new Date(System.currentTimeMillis()));
                            Phone phone = new Phone();
                            phone.setPhone(svf.getPhone());
                            service.withPhone(phone);
                            service.withCompanyFullName(svf.getCompanyFullName());
                            service.withServiceProviderStatus(ServiceProviderStatus.PENDENT);

                            Location newLocation = new Location();
                            newLocation.withFirstLineAddress(svf.getFirstLineAddress());
                            newLocation.withSecondLineAddress(svf.getFirstLineAddress());
                            newLocation.withCity(svf.getCity());
                            newLocation.withEirCode(svf.getEircode());

                            service.withLocation(newLocation);

                            try {
                                sRep.addToDB(service);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                            redirectToLogin();

                        }else{
                            Tools.alertMsg(form, "You have just subscribed! You will be redirected to Login Page", "Success");
                            Customer customer = new Customer();
                            customer.withPassword(cf.getPassword()); customer.withEmail(cf.getEmail());
                            Phone phone = new Phone();
                            phone.setPhone(cf.getPhone());
                            customer.withPhone(phone);
                            customer.withFirstName(cf.getFirstName());
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
                        Tools.alertError(form, msg, "Form not valid");
                    }
                }
            });
        });

        Arrays.asList(customerForm.get_1().getCancel(), serviceProviderForm.get_1().getCancel()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    redirectToLogin();
                }
            });
        });
    }

    private void redirectToLogin() {
        form.dispose();
        app.setFormController(null);
        app.login();
    }

    @Override
    public void addInputsAListener() {


        form.getRegistrationForm().getInputsPanel().get(5);

        form.getToggleServiceForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.withRegistrationForm(serviceProviderForm.get_1());

                validator = serviceProviderForm.get_2();

            }
        });

        form.getTogleCustomerForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                form.withRegistrationForm(customerForm.get_1());

                validator = customerForm.get_2();

            }
        });

    }





}
