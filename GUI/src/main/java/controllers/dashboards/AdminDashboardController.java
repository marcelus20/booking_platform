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
import javax.swing.border.Border;
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

public class AdminDashboardController implements Control {

    private final AdminDashboard dashboard;
    private final Admin user;
    private final ServiceProviderRepository sRep;
    private final Application app;
    private Tuple<List<Boolean>, AdminForm> adminForm;

    public AdminDashboardController(Application app) {
        user = (Admin) app.getUser();
        dashboard = new AdminDashboard();
        sRep = new ServiceProviderRepository();

        dashboard.validadeAndRepaint();

        addButtonsAFunction();
        addInputsAListener();
        this.app = app;
    }

    @Override
    public void addButtonsAFunction() {
        dashboard.getButtonsPanel().forEach(button->{
            button.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(button.getButton().getText().contains("activities")){
                        goToActivitiesPanel();
                    }else if(button.getButton().getText().contains("verify")){
                        try {
                            goToVerifyAServiceProviderView();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else if (button.getButton().getText().contains("another")){
                        goToAdminForm();
                    }else if (button.getButton().getText().contains("handle")){
                        goToComplaintsHandler();
                    }
                }

            });
        });
    }

    private void goToComplaintsHandler() {
        retrieveComplaintsData();
    }

    private void retrieveComplaintsData() {
        Repository<Complaint> complaintRepository = new ComplaintRepository();
        try {
            List<Complaint> complaints = complaintRepository.getList(null);
            System.out.println(complaints);
            String[][] complaintsArr = complaints.stream().map(complaint->
                    Arrays.asList(complaint.getComplaintID(),
                            complaint.getServiceId(),
                            complaint.getCustomerId(),
                            complaint.getComplaitStatus()+"",
                            complaint.getComplaint()
                    ).toArray(new String[5])).collect(Collectors.toList()).toArray(new String[complaints.size()][]);
            generateComplaintJTable(complaintsArr);

        } catch (SQLException e) {
            Tools.alertError(dashboard, "Not possible to load data, the mysql server might be down.", "Sad");
        }
    }

    private void generateComplaintJTable(String[][] complaintsArr) {
        String[] columns = {"ID", "Service ID", "Customer", "complaint status","Complaint Text"};
        JTable complaintsTable = new JTable(complaintsArr, columns);
        giveTableLinesAListener(complaintsTable);
        HandlingComplaints handlingComplaints = new HandlingComplaints(complaintsTable);
        dashboard.getOutput().removeAll();
        dashboard.getOutput().setLayout(new BorderLayout());
        dashboard.getOutput().add(handlingComplaints, BorderLayout.CENTER);
        dashboard.validadeAndRepaint();

    }

    private void giveTableLinesAListener(JTable complaintsTable) {
        complaintsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    Repository<ServiceProvider> serviceProviderRepository = new ServiceProviderRepository();
                    Repository<Customer> customerRepository = new CustomerRepository();
                    try {
                        HandlingComplaints handlingComplaints = new HandlingComplaints(complaintsTable);
                        dashboard.getOutput().removeAll();
                        dashboard.getOutput().setLayout(new BorderLayout());
                        dashboard.getOutput().add(handlingComplaints, BorderLayout.CENTER);
                        dashboard.validadeAndRepaint();
                        String serviceProviderName = serviceProviderRepository
                                .selectObjById(complaintsTable.getValueAt(complaintsTable
                                        .getSelectedRow(), 1)+"").getCompanyFullName();
                        String customerName = customerRepository.selectObjById(complaintsTable
                                .getValueAt(complaintsTable.getSelectedRow(), 2)+"").getFirstName();

                        String textArea = complaintsTable.
                                getValueAt(complaintsTable.getSelectedRow(), 4).toString();

                        String complaintID = complaintsTable.getValueAt(complaintsTable
                                .getSelectedRow(), 0)+"";
                        toggleHiddenHandlingArea(serviceProviderName, customerName, textArea, complaintID);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void toggleHiddenHandlingArea(String serviceProviderName, String customerName, String complaintText, String complaintID) {
        MyCustomJPanel handlingComplaintArea = new MyCustomJPanel("handling the complaint", 200,200);
        handlingComplaintArea.setLayout(new BorderLayout());
        JPanel names = new JPanel();
        String[] complaintStatus = {ComplaitStatus.PENDENT+"", ComplaitStatus.PROCESSING+"", ComplaitStatus.FINISHED+""};
        JComboBox<String> complaintsBox = new JComboBox<>(complaintStatus);
        names.setLayout(new GridLayout(2,1));
        names.add(new MyCustomJLabel("Service Name: "+serviceProviderName).getLabel());
        names.add(new MyCustomJLabel("Customer Name:"+customerName).getLabel());
        names.add(complaintsBox);
        giveComboBoxAlistener(complaintsBox, complaintID);
        JTextArea textArea = new JTextArea(complaintText);
        textArea.setEnabled(false);
        textArea.setPreferredSize(new Dimension(100,150));
        handlingComplaintArea.add(names, BorderLayout.CENTER);
        handlingComplaintArea.add(textArea, BorderLayout.SOUTH);
        dashboard.getOutput().add(handlingComplaintArea, BorderLayout.SOUTH);
        dashboard.validadeAndRepaint();
    }

    private void giveComboBoxAlistener(JComboBox<String> complaintsBox, String complaintID) {
        complaintsBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    ComplaintRepository complaintRepository = new ComplaintRepository();
                    complaintRepository.updateComplaint(complaintID, e.getItem());
                    Tools.alertMsg(dashboard,"You have successfully updated the status of this complaint", "success");
                    Log log = new Log(user.getId(), user.getEmail()+" update status of a complaint to "+e.getItem());
                    Tools.recordALogToDB(log);
                    goToComplaintsHandler();
                }
            }
        });
    }

    private void goToActivitiesPanel() {
        dashboard.getOutput().removeAll();
        MyCustomJPanel activitiesPanel = new MyCustomJPanel("Activities", 500,450);
        getActivitiesLogData(activitiesPanel);
        dashboard.getOutput().add(activitiesPanel);
        dashboard.validadeAndRepaint();

    }

    private void getActivitiesLogData(MyCustomJPanel activitiesPanel) {
        Repository<Log> logRepository = new LogRepository();
        List<Log> logs = null;
        try {
            logs = logRepository.getList(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[][] logsArray = logs.stream().map(log-> Arrays.asList(log.getUserId(), log.getActivityLog()).toArray(new String[2]))
                .collect(Collectors.toList()).toArray(new String[logs.size()][]);
        createLogsJTable(logsArray, activitiesPanel);
    }

    private void createLogsJTable(String[][] logsArray, MyCustomJPanel activitiesPanel) {
        String[] columns = {"user id", "activity"};
        JTable jTable = new JTable(logsArray, columns);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(500,450));
        activitiesPanel.getContent().add(jScrollPane);

    }

    private void goToAdminForm() {
        dashboard.getOutput().removeAll();
        MyCustomJPanel formPanel = new MyCustomJPanel("Admin Subscription Form", 500,450);

        AdminForm admForm = new AdminForm();
        admForm.getCancel().setEnabled(false);
        admForm.getSubmit().setEnabled(false);
        List<Boolean> validator = admForm.getInputsPanel().stream().map(inputPanel -> false).collect(Collectors.toList());
        adminForm = Tuple.tuple(validator, admForm);
        addFormsInputsAListener(adminForm.get_2());
        formPanel.getContent().add(adminForm.get_2());
        dashboard.getOutput().add(formPanel);
        dashboard.validadeAndRepaint();
        addSubmitAListener();
    }

    private void addSubmitAListener() {
        adminForm.get_2().getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                admin.withEmail(adminForm.get_2().getEmail());
                admin.withPassword(Tools.hashingPassword(adminForm.get_2().getConfirmPassword()));
                admin.withUserType(UserType.ADMIN);
                admin.withDateCreated(new Date(System.currentTimeMillis()));
                System.out.println(admin);
                Repository<Admin> admRep = null;

                admRep = new AdminRepository();

                try {
                    admRep.addToDB(admin);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                Tools.alertMsg(dashboard, admin.getEmail() + " has just been subscribed to the system", "Success");
                Log log = new Log(user.getId(), user.getEmail()+" subscribed " + admin.getEmail());
                Tools.recordALogToDB(log);
                goToAdminForm();
            }
        });
    }

    private void addFormsInputsAListener(AdminForm form) {
        adminForm.get_2().getInputsPanel().forEach(inputPanel -> {
            inputPanel.getInput().getInput().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validate(e);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validate(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    validate(e);
                }

                private void validate(DocumentEvent e) {
                    if(inputPanel.getLabel().getText().contains("email")){
                        if(Tools.validateEmail(adminForm.get_2().getEmail())){
                            adminForm.get_1().set(0, true);
                        }else{
                            adminForm.get_1().set(0, false);
                        }
                    }else{
                        if(Tools.validatePasswordsInput(adminForm.get_2().getPassword(),
                                adminForm.get_2().getConfirmPassword()) &&
                                Tools.validatePasswordCriteria(adminForm.get_2().getConfirmPassword())){
                            adminForm.get_1().set(1, true); adminForm.get_1().set(2, true);
                        }else{
                            adminForm.get_1().set(1, false); adminForm.get_1().set(2, false);
                        }
                    }
                    unblockSubmit();
                    System.out.println(adminForm.get_1());
                }
            });
        });
    }

    private void unblockSubmit() {
        if(adminForm.get_1().stream().reduce((acc,next)->acc && next).get()) {
            adminForm.get_2().getSubmit().setEnabled(true);
        }
    }


    private void goToVerifyAServiceProviderView() throws SQLException {
        dashboard.getOutput().removeAll();
        dashboard.getOutput().add(new ServiceProviderVerification());
        getServiceProvidersStatus();
        dashboard.validadeAndRepaint();
    }

    private void getServiceProvidersStatus() throws SQLException {

        List<ServiceProvider> listOfProviders = sRep
                .selectListOfServiceProvidersByStatus(null);


        String[][] table = Tools.mapListOfProvidersToArray(listOfProviders);

        JTable jTable = new JTable(table, new String[]{"email", "company", "Date subscribed", "Status", "phone", "add line 1", "add line 2", "city", "eir code"});
        Map<Integer, ServiceProvider> dictionary = mapTableWithServiceProviderList(jTable, listOfProviders);
        drawTable(jTable, dictionary);
    }

    private Map<Integer, ServiceProvider> mapTableWithServiceProviderList(JTable jTable, List<ServiceProvider> listOfProviders) {
        Map<Integer, ServiceProvider> dictionary = new HashMap<>();
        IntStream.range(0,jTable.getRowCount()).forEach(index -> dictionary.put(index, listOfProviders.get(index)));
        return dictionary;
    }

    private void drawTable(JTable jTable, Map<Integer, ServiceProvider> dictionary) {
        dashboard.getOutput().removeAll();
        dashboard.getServiceProviderVerification().withTableOfServices(jTable);
        dashboard.getServiceProviderVerification().getTableOfServices().setPreferredScrollableViewportSize(new Dimension(800,600));
        dashboard.getServiceProviderVerification().getContent().removeAll();
        dashboard.getServiceProviderVerification().getContent()
                .add(new JScrollPane(dashboard.getServiceProviderVerification().getTableOfServices()));
        dashboard.getOutput().add(dashboard.getServiceProviderVerification());
        dashboard.validadeAndRepaint();
        addTableAListener(dashboard.getServiceProviderVerification().getTableOfServices(),dictionary);
    }

    private void addTableAListener(JTable t, Map<Integer, ServiceProvider> dictionary) {
        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    String[] statusArray = Tools.getStringLIstOfAllServiceProviderStatus();
                    String chosenOption = Tools.alertComboBox(dashboard, statusArray, "Pick a status", "Status changer");
                    try {
                        ServiceProvider serviceProvider = dictionary.get(t.getSelectedRow());
                        serviceProvider.withServiceProviderStatus(ServiceProviderStatus.valueOf(chosenOption));

                        sRep.updateServiceProviderStatus(serviceProvider);
                        Tools.alertMsg(dashboard, "You have successfuly changed the status. " +
                                "You can change his/her status at anytime later on!", "");
                        Log log = new Log(user.getId(), user.getEmail()+" Changed status to " + chosenOption);
                        Tools.recordALogToDB(log);
                        goToVerifyAServiceProviderView();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


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
