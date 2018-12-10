package controllers;

import models.enums.ServiceProviderStatus;
import models.tuples.entitiesRepresentation.*;
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


/**
 * This class will control two rpublic registration forms, which is the form for customers and
 * services providers. Admins are not controlled by this class, cause Admins forms are not public, instead,
 * admins will be controlled by the AdminDashboardController class.
 */
public class FormController implements Control{

    /**
     * Attributes list
     * For the first 2 attributes in the top, which is a tuple. the length of
     * the booleand list is the number of textfields the forms have.
     * EG: Customer  Form has 5 fields, therefore the list of booleans will have size of 5, initially assgned to false.
     * (cause when form loads, it is not valid, user has to insert data first.)
     */
    private Tuple<ServiceProviderForm, List<Boolean>> serviceProviderForm;// tuple to map the service provider form with the list of booleans flags
    private Tuple<CustomerForm, List<Boolean>> customerForm; // tuple to map list of booleans to the customer form
    private Form form;// form view page
    private Application app; //the outter class that controls this class
    private List<Boolean> validator;
    private Repository<ServiceProvider> sRep; //the service provider database communicator
    private Repository<Customer> cRep;// the customer repository database communicator


    /**
     * Constructor
     * @param app
     */
    public FormController(Application app){
        CustomerForm cf = new CustomerForm();//initialises the form view (Customer is default)
        ServiceProviderForm svf = new ServiceProviderForm();// initialises service provider view
        //getting each every field of both forms and mapping them to the value of false
        customerForm = tuple(cf, cf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));
        serviceProviderForm = tuple(svf, svf.getInputsPanel().stream().map(i->false).collect(Collectors.toList()));
        //the currentform assigning, this case customer by default. get_1 corresponds to the form view itself
        //whereas get_2 corresponds to its boolean list validator, remember, this is a tuple!
        form = new Form(customerForm.get_1());
        this.app = app;
        validator = customerForm.get_2();
        //initialising the database communicators
        sRep = new ServiceProviderRepository();
        cRep = new CustomerRepository();
        //adding buttons and inputs a listener
        addButtonsAFunction();
        addInputsAListener();

    }

    /**
     * getter to the form attribute
     * @return
     */
    public Form getForm() {
        return form;
    }


    /**
     * This function will look after the the 1 and 2 index of the boolean validator list.
     * If it happens to password and confirm password field are equals and valids, the 1 and 2 indexes
     * will be set to true. So both are either true or false, there won't be a situation where
     * the confirm password field is true (valid) and password is false, or both are valid or not.
     * @param pass
     * @param confirmPass
     */
    private void validatePassword(String pass, String confirmPass){
        //checking them individually and reducing the value of both to unique boolean.
        Boolean passFlag = Arrays.asList(Tools.validatePasswordsInput(pass, confirmPass), Tools.validatePasswordCriteria(confirmPass))
                .stream().reduce(true,((acc, next)->acc && next));
        if(passFlag){// if both are valids
            validator.set(1,true); validator.set(2,true);
        }else{// if both are not valid
            validator.set(1, false); validator.set(2, false);
        }
    }

    /**
     * email is the first index mapped in the validator arrayList. If email meets the requirememnts, then set true.
     * The Tolls.validateEmail is making sure email is valid or not by the criteria of Upper, lower, numbers and symbols
     * @param email
     */
    private void validateEmail(String email){
        validator.set(0, Tools.validateEmail(email));
    }

    /**
     * This mehtod validates the phone number. For that just if string is not small enough
     * the correspondent index is 3 (4th text field in the form).
     * @param phone
     */
    private void validatePhone(String phone){
        if(Tools.validateStringsNonSmallerEqualsThan3(phone)) {
            validator.set(3, true);
        }else {
            validator.set(3, false);
        }
    }


    /**
     * validates if name isa string without numbers - index of the field in the view form is 4
     * @param companyName
     */
    private void validateCompanyFullName(String companyName){
        if(Tools.validateNotNumericStrings(companyName)) {
            validator.set(4, true);
        }else {
            validator.set(4, false);
        }
    }

    /**
     * This will pop up if the array of booleans is reduced to a false value.
     * (Means there is at least one field not valid in the whole form.)
     * @return
     */
    private String generateFormErrorMessage() {
        return new StringBuilder().append("The form is not valid, please follow the rules bellow:\n")
                .append(" * All fields must not be blank\n")
                .append(" * password must have between 8 and 20 length with at least \n")
                .append(" 1 Strange Symbol (@#$%), 1 Capital letter, 1 lower case and a number")
                .append(" * email field must be a valid email address").toString();
    }

    /**
     * In case this is a Service Provider form, after the succcessfull registration, the barber will
     * be notified about his pendent status is under admins verification. this function will pop up this message
     * @return
     */
    private String generateWarningMessage(){
        return new StringBuilder().append("You have just subscribed to the Booking Platform System\n")
                .append("Thank you very much!\n")
                .append("The next Step now is to wait one of the administrator to approve your subscription\n")
                .append("Bear in mind that your subscription request can be refused if your details do not seem to be genuine").toString();
    }


    /**
     * This function looks after the String last name validation. This case is just by checking if string is
     * has no numbers. Its correspondent index is 5 (6th input in the form.)
     * @param lastName
     */
    private void validateLastName(String lastName) {
        if(Tools.validateNotNumericStrings(lastName)){
            validator.set(5, true);
        }else{
            validator.set(5, false);
        }
    }

    /**
     * Validates the first name forms for Customers. It makes sure that the name has no numbers included in the string.
     * @param firstName
     */
    private void validateFirstName(String firstName) {
        if(Tools.validateNotNumericStrings(firstName)){
            validator.set(4, true);
        }else{
            validator.set(4, false);
        }
    }


    /**
     * This function validates the location part of the form, the first line and second line address, city, eircode.
     * The second line address is the only field in the whole form that is allowed a blank entry.
     * if first line address has text in it, it will will set wither first line address and seconf line address to true
     * regardless of the second line address state.
     */
    public void validateLocation(){

        /**
         * checking if firstline address is valid. by reducing the validation of first and second line together.
         */
        Boolean firstAndSecondLineAddress = Arrays.asList(Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(5).getInput().getInput().getText().trim()), Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(6).getInput().getInput().getText().trim()))
                .stream().reduce((acc, next )->acc || next).get();

        //if first line address is valid, so is the second line addres, then set them all to true
        if(firstAndSecondLineAddress){
            validator.set(5, true);  validator.set(6, true);
        }else{//if not, set them all to false.
            validator.set(5, false); validator.set(6, false);
        }
        //looking after the validation of the city field by checking if it is not small enough the length
        if(Tools.validateStringsNonSmallerEqualsThan3(serviceProviderForm.get_1()
                .getInputsPanel().get(7).getInput().getInput().getText().trim())){
            validator.set(7, true);
        }else{
            validator.set(7, false);
        }
        //looking after the EirCode by checking if the length is not  smaller than 1. like D instead of D9 and so on
        if(serviceProviderForm.get_1()
                .getInputsPanel().get(8).getInput().getInput().getText().trim().length() > 1){
            validator.set(8, true);
        }else{
            validator.set(8, false);
        }

    }

    /**
     * adding buttons a function, this case is the sign up and the cancel button that the fom view object window has.
     */
    @Override
    public void addButtonsAFunction() {
        CustomerForm cf = customerForm.get_1(); // assgning fields to local
        ServiceProviderForm svf = serviceProviderForm.get_1();//assigning field to local

        Arrays.asList(cf.getSubmit(), svf.getSubmit()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Tools.formValidator(()->{
                            //caling the validator functions independently when sign up is pressed
                            if(form.getRegistrationForm() instanceof CustomerForm){
                                validatePassword(cf.getPassword(), cf.getConfirmPassword());
                                validateEmail(cf.getEmail()); validatePhone(cf.getPhone());
                                validateFirstName(cf.getFirstName());
                                validateLastName(cf.getLastName());

                            //same with the service provider form
                            }else if (form.getRegistrationForm() instanceof ServiceProviderForm){
                                validatePassword(svf.getPassword(), svf.getConfirmPassword());
                                validateEmail(svf.getEmail()); validatePhone(svf.getPhone());
                                validateCompanyFullName(svf.getCompanyFullName());
                                validateLocation();
                            }
                            return null; // just to close the lambda expression this function just implements, doesnt return anything.
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    /**
                     * When reducing the result of the booleans flags to a unique valid or not valid flag,
                     * if valid, all of the contents in the text fields will be mapped to a
                     * ServiceProvider object before sendding to the databse.
                     */
                    if(validator.stream().reduce(true, (acc, next)->acc && next)){
                        if(form.getRegistrationForm() instanceof ServiceProviderForm){
                            Tools.alertWarning(form, generateWarningMessage(), "Your status is pendent");

                            /**
                             * Service Provider object encapsulation
                             */
                            ServiceProvider service = new ServiceProvider();
                            service.withPassword(svf.getPassword()); service.withEmail(svf.getEmail());// map password
                            service.withDateCreated(new Date(System.currentTimeMillis()));// fill up date
                            Phone phone = new Phone();// fill up phone
                            phone.setPhone(svf.getPhone());//populate phone attribute
                            service.withPhone(phone);// give service provider object a phone object
                            service.withCompanyFullName(svf.getCompanyFullName());// poulate the company name in the object
                            service.withServiceProviderStatus(ServiceProviderStatus.PENDENT); // set status to pendent
                            /**
                             * Still in service provider object, preapre the Location objet before assigning it to Service Provider
                             */
                            Location newLocation = new Location();// declaring location object
                            newLocation.withFirstLineAddress(svf.getFirstLineAddress()); // populating 1st lineaddress
                            newLocation.withSecondLineAddress(svf.getFirstLineAddress());// populating 2nd Line address
                            newLocation.withCity(svf.getCity());// populating city
                            newLocation.withEirCode(svf.getEircode());//populating eircode
                            /**
                             * finally assigning service provider object with a location attribute.
                             */
                            service.withLocation(newLocation);


                            /**
                             * At this point the service provider object is good to go. All its attributes have been filled up
                             * so attributes can be mapped to DB table schema.
                             */
                            try {
                                //finally adding this new Service provider to DB
                                sRep.addToDB(service);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            //go back to Login after this.
                            redirectToLogin();
                        }else{
                            /**
                             * If code reaches here, means that the form that has been filled is the Customer Formm.
                             * Therefore the same proccess above will happen, but this time, with the Customer object
                             * encapsulation.
                             */
                            Tools.alertMsg(form, "You have just subscribed! You will be redirected to Login Page", "Success");
                            Customer customer = new Customer();// declaring customer instance
                            customer.withPassword(cf.getPassword()); customer.withEmail(cf.getEmail());//populating password and email
                            Phone phone = new Phone();// declaring phone object
                            phone.setPhone(cf.getPhone()); // populating phone attribute of phone object
                            customer.withPhone(phone); // giving customer a phone
                            customer.withFirstName(cf.getFirstName()); // populating first name attribute
                            customer.withLastName(cf.getLastName()); // populating second name attribute
                            customer.withDateCreated(new Date(System.currentTimeMillis()));//giving a date of registration to customer
                            /**
                             * at this point, customer object is good to go, to be saved in the DB
                             */
                            try {
                                cRep.addToDB(customer);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            redirectToLogin(); // going back to login
                        }

                    }else{
                        /**
                         * if code reaches here, means that the booleans lists wasn not reduced to true.
                         */
                        String msg = generateFormErrorMessage();
                        Tools.alertError(form, msg, "Form not valid");
                    }
                }
            });
        });

        //giving the button cancel for customer form and service form, a functionality, which is going back to login
        Arrays.asList(customerForm.get_1().getCancel(), serviceProviderForm.get_1().getCancel()).forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    redirectToLogin(); // go to login if the cancel of one of the forms has been pressed
                }
            });
        });
    }

    /**
     * This function will kill the form registration window and lead the user to the login page.
     */
    private void redirectToLogin() {
        form.dispose();
        app.setFormController(null);
        app.login();
    }

    /**
     * This function will give the JMenuITem in the frame a functionality. when user choses the toggleServiceForm,
     * then the service form will show up in the view, else, the customer form will show up
     */
    @Override
    public void addInputsAListener() {
        form.getRegistrationForm().getInputsPanel().get(5);

        /**
         * poping up service fregistration form
         */
        form.getToggleServiceForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.withRegistrationForm(serviceProviderForm.get_1());// fill view with serviceFrom
                validator = serviceProviderForm.get_2(); // change validator to service validateor form
            }
        });

        /**
         * popping up the customer registration form
         */
        form.getTogleCustomerForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.withRegistrationForm(customerForm.get_1());// filling up view with customer form
                validator = customerForm.get_2(); // changing validator to customer validator
            }
        });
    }
}
