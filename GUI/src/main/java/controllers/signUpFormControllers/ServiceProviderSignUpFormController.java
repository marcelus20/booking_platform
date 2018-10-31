package controllers.signUpFormControllers;

import interfaces.Controlls;
import interfaces.Repository;
import interfaces.ViewsObjectGetter;
import models.Location;
import models.users.ServiceProvider;
import repository.ServiceProviderRepository;
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


    public ServiceProviderSignUpFormController() throws SQLException {
        serviceProviderSignUpForm = new ServiceProviderSignUpForm();
        locationForm = LocationFormController.initServiceProviderFormController().getViewObject();
        sRep = new ServiceProviderRepository();

        config();
        setSizes();
        build();
    }

    public static ServiceProviderSignUpFormController initServiceProviderSignUpFormController() throws SQLException {
        return new ServiceProviderSignUpFormController();
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
        buttonsPanel.add(serviceProviderSignUpForm.getCancel(), BorderLayout.LINE_END);
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

                serviceProvider.withLocations(location);

                System.out.println(serviceProvider);

                sRep.insertData(serviceProvider);
                serviceProviderSignUpForm.getStatusForm()
                        .setText("<html><body>Your request to subscribe as a service provider has been sent. <br>" +
                                "One of the admins will take the request and approve or disapprove. </body></html>");

            }
        });
    }

}
