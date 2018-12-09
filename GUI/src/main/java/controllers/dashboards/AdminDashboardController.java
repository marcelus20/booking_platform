package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.enums.ComplaitStatus;
import models.enums.ServiceProviderStatus;
import models.enums.UserType;
import models.repositories.*;
import models.tuples.Tuple;
import models.tuples.entitiesRepresentation.*;
import models.utils.Tools;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;
import views.dashboard.admin.AdminDashboard;
import views.dashboard.admin.HandlingComplaints;
import views.dashboard.admin.ServiceProviderVerification;
import views.forms.AdminForm;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * This class is to be used by an admin. This controller will controll the adminDashboard and will
 * implement the Control interface as the dashboards all have buttons and inputs to add listener.
 * Admins will be able to See activities of users (Consult table log), verify a service provider,
 * create another admin and handle complaints
 */
public class AdminDashboardController implements Control {

    /**
     * Attributes
     */
    private final AdminDashboard dashboard;// admindashboard
    private final Admin user;//admin logged in
    private final ServiceProviderRepository sRep; //db agent instance for the service provider table
    private final Application app;// application instance
    private Tuple<List<Boolean>, AdminForm> adminForm;//admin form validation

    /**
     * Constructor: gets an Application object and populates app field.
     * @param app
     */
    public AdminDashboardController(Application app) {
        user = (Admin) app.getUser(); // admin logged in casted to Admin
        dashboard = new AdminDashboard();// initialising dashboard view
        sRep = new ServiceProviderRepository();// initialising DB handler agent
        dashboard.validadeAndRepaint();
        addButtonsAFunction();// override method to add function to buttons
        addInputsAListener();//same with the inputs
        this.app = app;
    }

    /**
     * Adds functions to the buttons contained in the dashboard sideBar (Options)
     */
    @Override
    public void addButtonsAFunction() {
        dashboard.getButtonsPanel().forEach(button->{
            button.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(button.getButton().getText().contains("activities")){//go to activities log
                        goToActivitiesPanel();
                    }else if(button.getButton().getText().contains("verify")){// go to serviceProvider verification panel
                        try {
                            goToVerifyAServiceProviderView();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else if (button.getButton().getText().contains("another")){// go to new Admin form
                        goToAdminForm();
                    }else if (button.getButton().getText().contains("handle")){//go to handle a complaint
                        goToComplaintsHandler();
                    }
                }

            });
        });
    }

    /**
     * initialises the process of showing the complaints panel
     */
    private void goToComplaintsHandler() {
        retrieveComplaintsData();// get from db complaints
    }

    /**
     * With the use of the complaintRepository object, this function retrieve the list of complaints in the database
     */
    private void retrieveComplaintsData() {
        Repository<Complaint> complaintRepository = new ComplaintRepository();//declaring db communicator
        try {
            List<Complaint> complaints = complaintRepository.getList(null);// getting list of all complaints
            //converting list into a 2D array by using lambda expression from complaints list
            String[][] complaintsArr = complaints.stream().map(complaint->
                    Arrays.asList(complaint.getComplaintID(),
                            complaint.getServiceId(),
                            complaint.getCustomerId(),
                            complaint.getComplaitStatus()+"",
                            complaint.getComplaint()
                    ).toArray(new String[5])).collect(Collectors.toList()).toArray(new String[complaints.size()][]);
            //creating jtable out of this process
            generateComplaintJTable(complaintsArr);
        } catch (SQLException e) {
            //in case everything goes wrong
            Tools.alertError(dashboard, "Not possible to load data, the mysql server might be down.", "Sad");
        }
    }

    /**
     * After data has been retrieved and converted into a 2D array, this method will create the actual
     * JTable and render this to dashboard view.
     * @param complaintsArr
     */
    private void generateComplaintJTable(String[][] complaintsArr) {
        // creating columns
        String[] columns = {"ID", "Service ID", "Customer", "complaint status","Complaint Text"};
        //declaring JTable
        JTable complaintsTable = new JTable(complaintsArr, columns);
        //giving table a listener
        giveTableLinesAListener(complaintsTable);
        //declaring the view panel that will display the complaint
        HandlingComplaints handlingComplaints = new HandlingComplaints(complaintsTable);
        //mounting dashboard to show handlingComplaints panel
        dashboard.getOutput().removeAll();
        dashboard.getOutput().setLayout(new BorderLayout());
        dashboard.getOutput().add(handlingComplaints, BorderLayout.CENTER);
        dashboard.validadeAndRepaint();

    }


    /**
     * This function will add a functionality to the selected row by clicking in the complaint Jtable view object.
     * the row holds the customer and service provider id, with this it will be possible to
     * get their names and display in the panel that will toggle when clicking on the table row
     * @param complaintsTable
     */
    private void giveTableLinesAListener(JTable complaintsTable) {
        complaintsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){//after it is adjusted, so prevent from executing twice
                    try {
                        //creating database communicators
                        Repository<ServiceProvider> serviceProviderRepository = new ServiceProviderRepository();
                        Repository<Customer> customerRepository = new CustomerRepository();
                        //reloading handling complaints panel with the table, but at this time with the
                        //toggled panel beneath the table
                        HandlingComplaints handlingComplaints = new HandlingComplaints(complaintsTable);
                        dashboard.getOutput().removeAll();
                        dashboard.getOutput().setLayout(new BorderLayout());// setting to borderLayout
                        dashboard.getOutput().add(handlingComplaints, BorderLayout.CENTER);
                        dashboard.validadeAndRepaint();
                        //downloading and saving service provider name
                        String serviceProviderName = serviceProviderRepository
                                .selectObjById(complaintsTable.getValueAt(complaintsTable
                                        .getSelectedRow(), 1)+"").getCompanyFullName();
                        //downloading and saving customer name
                        String customerName = customerRepository.selectObjById(complaintsTable
                                .getValueAt(complaintsTable.getSelectedRow(), 2)+"").getFirstName();

                        //retrieving the complaint text from the table
                        String textArea = complaintsTable.
                                getValueAt(complaintsTable.getSelectedRow(), 4).toString();

                        //retrieving complaint ID from table view
                        String complaintID = complaintsTable.getValueAt(complaintsTable
                                .getSelectedRow(), 0)+"";
                        //showing the handling area
                        toggleHiddenHandlingArea(serviceProviderName, customerName, textArea, complaintID);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * This function displays the users involved to the complaint, the name of the service provider and user
     * and display an non editable text area for the admin to read the complaint text and change the status
     * of the complaint
     * @param serviceProviderName
     * @param customerName
     * @param complaintText
     * @param complaintID
     */
    private void toggleHiddenHandlingArea(String serviceProviderName, String customerName, String complaintText, String complaintID) {
        //creating the panel
        MyCustomJPanel handlingComplaintArea = new MyCustomJPanel("handling the complaint", 200,200);
        //setting to border layout
        handlingComplaintArea.setLayout(new BorderLayout());
        JPanel names = new JPanel();
        //creating JCombobox with the array of the string versions of the complaint Status Enum
        String[] complaintStatus = {ComplaitStatus.PENDENT+"", ComplaitStatus.PROCESSING+"", ComplaitStatus.FINISHED+""};
        JComboBox<String> complaintsBox = new JComboBox<>(complaintStatus);
        names.setLayout(new GridLayout(2,1));
        names.add(new MyCustomJLabel("Service Name: "+serviceProviderName).getLabel());
        names.add(new MyCustomJLabel("Customer Name:"+customerName).getLabel());
        names.add(complaintsBox);
        giveComboBoxAlistener(complaintsBox, complaintID);
        JTextArea textArea = new JTextArea(complaintText);
        textArea.setEnabled(false);// setting textArea to false
        textArea.setPreferredSize(new Dimension(100,150));
        handlingComplaintArea.add(names, BorderLayout.CENTER);
        handlingComplaintArea.add(textArea, BorderLayout.SOUTH);
        dashboard.getOutput().add(handlingComplaintArea, BorderLayout.SOUTH);//finally adding this panel to dashboard
        dashboard.validadeAndRepaint();
    }

    /**
     * Everytime an admin changes the selected item in the combobox, it will update in the database acorddingly with the value
     * Eg: if changes to Finished, that booking will be updated in the system with the finished status.
     * @param complaintsBox
     * @param complaintID
     */
    private void giveComboBoxAlistener(JComboBox<String> complaintsBox, String complaintID) {
        complaintsBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    ComplaintRepository complaintRepository = new ComplaintRepository();//creating db communicator
                    complaintRepository.updateComplaint(complaintID, e.getItem());//updating booking with the selected item in combobox
                    Tools.alertMsg(dashboard,"You have successfully updated the status of this complaint", "success");
                    //recording logs
                    Log log = new Log(user.getId(), user.getEmail()+" update status of a complaint to "+e.getItem());
                    Tools.recordALogToDB(log);
                    goToComplaintsHandler();//reloading page
                }
            }
        });
    }

    /**
     * This function is triggered when user hits the button to go to activities panel.
     * this will clear all content in dashboard and starts adding again with the activity panel
     */
    private void goToActivitiesPanel() {
        dashboard.getOutput().removeAll();//clear
        //initialises the activities view object
        MyCustomJPanel activitiesPanel = new MyCustomJPanel("Activities", 500,450);
        //retrieve from db the logs
        getActivitiesLogData(activitiesPanel);
        //show panel in dashboard
        dashboard.getOutput().add(activitiesPanel);
        dashboard.validadeAndRepaint();

    }

    /**
     * This function will download all activities from DB and call the createLogs jTable for showing in dashboard
     * @param activitiesPanel
     */
    private void getActivitiesLogData(MyCustomJPanel activitiesPanel) {
        try {
            Repository<Log> logRepository = new LogRepository();//declaring database communicator
            List<Log> logs;//retrieving list of logs
            logs = logRepository.getList(null);
            //converting list into a 2D array
            String[][] logsArray = logs.stream().map(log-> Arrays.asList(log.getUserId(), log.getActivityLog()).toArray(new String[2]))
                    .collect(Collectors.toList()).toArray(new String[logs.size()][]);
            //finally creating JTable
            createLogsJTable(logsArray, activitiesPanel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will create the Jtable instance, add it into a scroll pane and finally add it to the
     * view ActiitiesPanel MyCustomJPanel object.
     * @param logsArray
     * @param activitiesPanel
     */
    private void createLogsJTable(String[][] logsArray, MyCustomJPanel activitiesPanel) {
        String[] columns = {"user id", "activity"};
        JTable jTable = new JTable(logsArray, columns);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(500,450));
        activitiesPanel.getContent().add(jScrollPane);

    }

    /**
     * This function leads the user to creating a new admin form page.
     * The AdminForm object view comes with a cancel button, which turns out to be useless in this case,
     * cause the cancel action could be done by an admin clicking in another button to go to another panel.
     * So, in this case, it will be set to not editable.
     */
    private void goToAdminForm() {
        dashboard.getOutput().removeAll();//clears dashboard
        //initialises the form panel
        MyCustomJPanel formPanel = new MyCustomJPanel("Admin Subscription Form", 500,450);
        AdminForm admForm = new AdminForm();
        admForm.getCancel().setEnabled(false);//this button will remain not editable
        admForm.getSubmit().setEnabled(false);// this button will be set to editable when form is valid
        List<Boolean> validator = admForm.getInputsPanel().stream().map(inputPanel -> false).collect(Collectors.toList());
        adminForm = Tuple.tuple(validator, admForm); // initialising the adminForm Tuple
        addFormsInputsAListener(adminForm.get_2()); // adding a listener to the forms fields and button for the validation step
        formPanel.getContent().add(adminForm.get_2()); // adding to view component
        dashboard.getOutput().add(formPanel);
        dashboard.validadeAndRepaint();
        addSubmitAListener();// adding the submit button a listener
    }

    /**
     * This function will add a listener to submit button of the Admin registration form.
     * Will be held not editable until the form is valid.
     */
    private void addSubmitAListener() {
        adminForm.get_2().getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Admin admin = new Admin();//decaring admin object
                    admin.withEmail(adminForm.get_2().getEmail()); // populating email with the field email
                    admin.withPassword(Tools.hashingPassword(adminForm.get_2().getConfirmPassword()));//same with password
                    admin.withUserType(UserType.ADMIN);//hardcoding the user type to admin, cause it is an admin
                    admin.withDateCreated(new Date(System.currentTimeMillis()));// populating the date of registration
                    Repository<Admin> admRep = new AdminRepository(); // creating the database communicator
                    admRep.addToDB(admin); // adding new admin to DB
                    Tools.alertMsg(dashboard, admin.getEmail() + " has just been subscribed to the system", "Success");
                    //logging operation
                    Log log = new Log(user.getId(), user.getEmail()+" subscribed " + admin.getEmail());
                    Tools.recordALogToDB(log);
                    goToAdminForm();// reloading page
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * This function will be responsible to handle the form validation tuple.
     * for all the interacted situations with the form, whether insertUpdate, removeUpdate  and changeUpdate
     * event, it will lead to the function validate that gets the event itself.
     * @param form
     */
    private void addFormsInputsAListener(AdminForm form) {
        adminForm.get_2().getInputsPanel().forEach(inputPanel -> {
            inputPanel.getInput().getInput().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validate(e); //call validate
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validate(e); // call validate
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    validate(e); // call validate
                }

                /**
                 * This function will set the boolean list of the tuple field adminForm
                 * to true if the fields meet the requirements.
                 * the Tools class has a method that validate email and passwords criteria.
                 * @param e
                 */
                private void validate(DocumentEvent e) {
                    if(inputPanel.getLabel().getText().contains("email")){
                        if(Tools.validateEmail(adminForm.get_2().getEmail())){// if email is valid
                            adminForm.get_1().set(0, true);//true
                        }else{
                            adminForm.get_1().set(0, false);//false
                        }
                    }else{
                        //if password follows all criteria
                        if(Tools.validatePasswordsInput(adminForm.get_2().getPassword(),
                                adminForm.get_2().getConfirmPassword()) &&
                                Tools.validatePasswordCriteria(adminForm.get_2().getConfirmPassword())){
                            adminForm.get_1().set(1, true); adminForm.get_1().set(2, true);// set to true
                        }else{
                            adminForm.get_1().set(1, false); adminForm.get_1().set(2, false);// set to false
                        }
                    }
                    unblockSubmit();//unblock if the boolean list of tuple has all elements true
                }
            });
        });
    }

    /**
     * This function will set Submit button to enable if all elements in the tuple are true.:
     * Eg: if boolean array is {true, true, false}, it will return false as this function returns
     * true && true && false = false.
     *
     * The reduce method loops through the list and accumulates the result of true && true and so on
     * until list ends and gets the final results, either true or false
     */
    private void unblockSubmit() {
        if(adminForm.get_1().stream().reduce((acc,next)->acc && next).get()) {
            adminForm.get_2().getSubmit().setEnabled(true);// set to true
        }
    }


    /**
     * This function leads user to the provider veirfication page.
     * @throws SQLException
     */
    private void goToVerifyAServiceProviderView() throws SQLException {
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(new ServiceProviderVerification());// initialising view panel
        getServiceProvidersStatus();// downloading list of services from database
        dashboard.validadeAndRepaint();
    }

    /**
     * gets list of service providers from db and creates the JTable with the data
     * @throws SQLException
     */
    private void getServiceProvidersStatus() throws SQLException {
        List<ServiceProvider> listOfProviders = sRep // downloading serviceProvider list from database
                .selectListOfServiceProvidersByStatus(null);
        //converting list into a 2D array using stream.map function
        String[][] table = listOfProviders.stream().map(serviceProvider ->
                //add email, comany name, date, status, phone and entire address to one array.
                //add each lines to an array too.
                Arrays.asList(
                        serviceProvider.getEmail(), serviceProvider.getCompanyFullName(),
                        serviceProvider.getDateCreated()+"", serviceProvider.getStatus()+"",
                        serviceProvider.getPhone().getPhone(), serviceProvider.getLocation().getFirstLineAddress(),
                        serviceProvider.getLocation().getSecondLineAddress(), serviceProvider.getLocation().getCity(),
                        serviceProvider.getLocation().getEirCode()
                ).toArray(new String[9]))
                .collect(Collectors.toList()).toArray(new String[listOfProviders.size()][]);

        //creatig JTable with the 2D array generated above
        JTable jTable = new JTable(table, new String[]{"email", "company", "Date subscribed", "Status", "phone", "add line 1", "add line 2", "city", "eir code"});
        //dictionary to map each line to the service provider
        Map<Integer, ServiceProvider> dictionary = mapTableWithServiceProviderList(jTable, listOfProviders);
        //finaly showing table to view
        drawTable(jTable, dictionary);
    }

    /**
     * This function maps the table passed as parameter row to an element in the service provider list.
     * @param jTable
     * @param listOfProviders
     * @return
     */
    private Map<Integer, ServiceProvider> mapTableWithServiceProviderList(JTable jTable, List<ServiceProvider> listOfProviders) {
        Map<Integer, ServiceProvider> dictionary = new HashMap<>();
        IntStream.range(0,jTable.getRowCount()).forEach(index -> dictionary.put(index, listOfProviders.get(index)));
        return dictionary;
    }

    /**
     * This function will get the table of service providers and fetch it to dashboard view
     * this will also configure the table size and call the addTableAlistener method
     * @param jTable
     * @param dictionary
     */
    private void drawTable(JTable jTable, Map<Integer, ServiceProvider> dictionary) {
        dashboard.getOutput().removeAll();//clear
        dashboard.getServiceProviderVerification().withTableOfServices(jTable);//passing Jtable to view component
        dashboard.getServiceProviderVerification().getTableOfServices().setPreferredScrollableViewportSize(new Dimension(800,600));
        dashboard.getServiceProviderVerification().getContent().removeAll();
        dashboard.getServiceProviderVerification().getContent()
                .add(new JScrollPane(dashboard.getServiceProviderVerification().getTableOfServices()));
        dashboard.getOutput().add(dashboard.getServiceProviderVerification());
        dashboard.validadeAndRepaint();
        //adding table a listener
        addTableAListener(dashboard.getServiceProviderVerification().getTableOfServices(),dictionary);
    }

    /**
     * Adds an listener to table row and maps that row to an dictionary value, which is the service provider
     * correspondent to that row.
     * @param t
     * @param dictionary
     */
    private void addTableAListener(JTable t, Map<Integer, ServiceProvider> dictionary) {
        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    try {
                        //getting the combobox options from the Tools class function
                        String[] statusArray = Tools.getStringLIstOfAllServiceProviderStatus();
                        //creating combobox object
                        String chosenOption = Tools.alertComboBox(dashboard, statusArray, "Pick a status", "Status changer");
                        //retrieving service provider correspondent to that line
                        ServiceProvider serviceProvider = dictionary.get(t.getSelectedRow());
                        //updating service provider status in memory object
                        serviceProvider.withServiceProviderStatus(ServiceProviderStatus.valueOf(chosenOption));
                        //updating in database
                        sRep.updateServiceProviderStatus(serviceProvider);
                        Tools.alertMsg(dashboard, "You have successfuly changed the status. " +
                                "You can change his/her status at anytime later on!", "");
                        //recording log
                        Log log = new Log(user.getId(), user.getEmail()+" Changed status to " + chosenOption);
                        Tools.recordALogToDB(log);
                        goToVerifyAServiceProviderView();// reloading page
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * Logout function. leads to the login page.
     */
    @Override
    public void addInputsAListener() {
        dashboard.getMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.dispose();
                app.setUser(null);
                app.login();
            }
        });
    }
}
