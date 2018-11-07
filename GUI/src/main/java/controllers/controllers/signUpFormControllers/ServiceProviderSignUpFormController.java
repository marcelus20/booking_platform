package controllers.controllers.signUpFormControllers;

import controllers.controllers.Controlls;
import models.repositories.Repository;
import controllers.controllers.ViewsObjectGetter;
import models.entities.Location;
import models.entities.ServiceProvider;
import models.repositories.ServiceProviderRepository;
import views.signUpForms.LocationForm;
import views.signUpForms.ServiceProviderSignUpForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ServiceProviderSignUpFormController
        implements Controlls<ServiceProviderSignUpForm>,
        ViewsObjectGetter<ServiceProviderSignUpForm>{

    private ServiceProviderSignUpForm serviceProviderSignUpForm;
    private LocationForm locationForm;
    private Repository<ServiceProvider> sRep;


    private ServiceProviderSignUpFormController() throws SQLException {
        serviceProviderSignUpForm = new ServiceProviderSignUpForm();
        locationForm = LocationFormController.initServiceProviderFormController().getViewObject();
        sRep = new ServiceProviderRepository();

        config();
        setSizes();
        build();
    }

    private ServiceProviderSignUpFormController(ServiceProvider user) {
        serviceProviderSignUpForm = new ServiceProviderSignUpForm();
        locationForm = LocationFormController.initServiceProviderFormController().getViewObject();

        serviceProviderSignUpForm.getFormName().setText("Updating datils");
        serviceProviderSignUpForm.getCompanyFullName().getInput().setText(user.getCompanyFullName());
        serviceProviderSignUpForm.getPhoneNumber().getInput().setText(user.getPhone());
        serviceProviderSignUpForm.getEmail().getInput().setText(user.geteMail());

        locationForm.getCity().getInput().setText(user.getLocations().getCity());
        locationForm.getEirCode().getInput().setText(user.getLocations().getEir_code());
        locationForm.getFirstLineAddress().getInput().setText(user.getLocations().getFirst_line_address());
        locationForm.getSecondLineAddress().getInput().setText(user.getLocations().getSecond_line_address());
        serviceProviderSignUpForm.setCancel(null);
        config();
        setSizes();
        build();
    }

    public static ServiceProviderSignUpFormController initServiceProviderSignUpFormController() throws SQLException {
        return new ServiceProviderSignUpFormController();
    }

    public static ServiceProviderSignUpFormController initServiceProviderSignUpFormController(ServiceProvider user){
        return new ServiceProviderSignUpFormController(user);
    }


    @Override
    public void config() {
        serviceProviderSignUpForm.setLayout(new GridLayout(0,1));
        assignButtonsAFunction();
    }

    @Override
    public void build() {
        serviceProviderSignUpForm.add(serviceProviderSignUpForm.getFormName());
        serviceProviderSignUpForm.add(serviceProviderSignUpForm.getCompanyFullName());
        serviceProviderSignUpForm.add(serviceProviderSignUpForm.getEmail());
        serviceProviderSignUpForm.add(serviceProviderSignUpForm.getPassword());
        serviceProviderSignUpForm.add(serviceProviderSignUpForm.getPhoneNumber());
        serviceProviderSignUpForm.add(locationForm.getFirstLineAddress());
        serviceProviderSignUpForm.add(locationForm.getSecondLineAddress());
        serviceProviderSignUpForm.add(locationForm.getCity());
        serviceProviderSignUpForm.add(locationForm.getEirCode());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(serviceProviderSignUpForm.getStatusForm(), BorderLayout.NORTH);
        buttonsPanel.add(serviceProviderSignUpForm.getSubmit(), BorderLayout.LINE_START);
        if(serviceProviderSignUpForm.getCancel() != null){
            buttonsPanel.add(serviceProviderSignUpForm.getCancel(), BorderLayout.LINE_END);
        }

        serviceProviderSignUpForm.add(buttonsPanel);
    }

    @Override
    public void setSizes() {
        serviceProviderSignUpForm.getFormName().setFont(new Font("Courier", Font.BOLD,30));
    }

    @Override
    public ServiceProviderSignUpForm getViewObject() {
        return serviceProviderSignUpForm;
    }

    private void assignButtonsAFunction(){
        serviceProviderSignUpForm.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Location location = new Location();
                ServiceProvider serviceProvider = new ServiceProvider();

                serviceProvider.withCompanyFullName(serviceProviderSignUpForm.getCompanyFullName().getInput().getText());
                serviceProvider.withEmail(serviceProviderSignUpForm.getEmail().getInput().getText());
                serviceProvider.withPassword(serviceProviderSignUpForm.getPassword().getInput().getText());
                serviceProvider.withPhone(serviceProviderSignUpForm.getPhoneNumber().getInput().getText());

                location.withFirstLineAddress(locationForm.getFirstLineAddress().getInput().getText());
                location.withSecondLineAddress(locationForm.getSecondLineAddress().getInput().getText());
                location.withCity(locationForm.getCity().getInput().getText());
                location.withEirCode(locationForm.getEirCode().getInput().getText());

                serviceProvider.withLocation(location);

                System.out.println(serviceProvider);

                try {
                    sRep.insertData(serviceProvider);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                serviceProviderSignUpForm.getStatusForm()
                        .setText("<html><body>Your request to subscribe as a service provider has been sent. <br>" +
                                "One of the admins will take the request and approve or disapprove. </body></html>");

            }
        });


    }



}
