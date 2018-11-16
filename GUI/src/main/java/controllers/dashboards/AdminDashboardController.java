package controllers.dashboards;

import controllers.Application;
import controllers.Control;
import models.enums.ServiceProviderStatus;
import models.enums.UserType;
import models.repositories.AdminRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import models.tuples.Tuple;
import models.tuples.entitiesRepresentation.Admin;
import models.utils.Tools;
import views.customComponents.MyCustomJPanel;
import views.dashboard.admin.AdminDashboard;
import views.dashboard.admin.ServiceProviderVerification;
import views.forms.AdminForm;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminDashboardController implements Control {

    private final AdminDashboard dashboard;
    private final Admin user;
    private final ServiceProviderRepository sRep;
    private final Application app;
    private Tuple<List<Boolean>, AdminForm> adminForm;

    public AdminDashboardController(Application app) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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
                        Tools.alertMsg(dashboard, "activities", "");
                    }else if(button.getButton().getText().contains("verify")){
                        try {
                            goToVerifyAServiceProviderView();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else if (button.getButton().getText().contains("another")){
                        goToAdminForm();
                    }
                }

            });
        });
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
                try {
                    admRep = new AdminRepository();
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
                try {
                    admRep.addToDB(admin);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                Tools.alertMsg(dashboard, admin.getEmail() + " has just been subscribed to the system", "Success");
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
        Map<String, List<String>> map = new HashMap<>();

        List<Tuple<String, List<String>>> listOfResults = sRep
                .selectListOfServiceProvidersByStatus(ServiceProviderStatus.PENDENT);

        listOfResults.forEach(tuple->map.put(tuple.get_1(), tuple.get_2()));

        creatingJTable(map);
    }

    private void creatingJTable(Map<String, List<String>> map) {
        List<List<String>> tableAsList = new ArrayList<>();
        map.forEach((key,value)->tableAsList.add(value));

        Map<Integer, String> rowIndexToIdMap = new HashMap<>();

//        String[][] table = Tools.convert2DlistTo2DArray(tableAsList);

//        JTable tableOfServices = new JTable(table, new String[]{"id","email", "phone", "company name", "status", "Address", "second line address", "city"});

//        drawTableToView(tableOfServices, map);
    }

    private void drawTableToView(JTable tableOfServices, Map<String, List<String>> map) {
        dashboard.getOutput().removeAll();
        dashboard.getServiceProviderVerification().withTableOfServices(tableOfServices);
        dashboard.getServiceProviderVerification().getContent().add(dashboard.getServiceProviderVerification().getTableOfServices());
        dashboard.getOutput().add(dashboard.getServiceProviderVerification());
        dashboard.validadeAndRepaint();
        addTableAListener(dashboard.getServiceProviderVerification().getTableOfServices(),map);
    }

    private void addTableAListener(JTable t, Map<String, List<String>> map) {
        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    String id = (String) t.getValueAt(t.getSelectedRow(), 0);
                    String[] statusArray = Tools.getStringLIstOfAllServiceProviderStatus();
                    String chosenOption = Tools.alertComboBox(dashboard, statusArray, "Pick a status", "Status changer");

                    try {
                        sRep.updateServiceProviderStatus(ServiceProviderStatus.valueOf(chosenOption.toUpperCase()), id);
                        Tools.alertMsg(dashboard, "You have successfuly changed the status. " +
                                "As this service provider is no longer pendent, it will not show in this table anymore!", "");
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
