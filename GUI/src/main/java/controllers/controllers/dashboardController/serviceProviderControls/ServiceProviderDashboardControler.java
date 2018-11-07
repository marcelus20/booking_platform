package controllers.controllers.dashboardController.serviceProviderControls;

import controllers.controllers.dashboardController.DashboardController;
import models.entities.ServiceProvider;
import views.dashboard.Dashboard;
import views.signUpForms.ServiceProviderSignUpForm;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import static controllers.controllers.dashboardController.serviceProviderControls.BookingSlotsController.initBookingSlotsController;
import static controllers.controllers.dashboardController.serviceProviderControls.ToggleButtonsController.initTogleButtonsController;
import static controllers.controllers.signUpFormControllers.ServiceProviderSignUpFormController.initServiceProviderSignUpFormController;
import static controllers.controllers.dashboardController.serviceProviderControls.ServiceProviderBookingListController.initServiceProviderBookingListController;

public class ServiceProviderDashboardControler {

    private BookingSlotsController bookingSlotsController;
    private ToggleButtonsController toggleButtonsController;
    private ServiceProviderSignUpForm serviceProviderSignUpForm;
    private ServiceProviderBookingListController serviceProviderBookingListController;
    private ServiceProvider user;
    private Dashboard dashboard;

    public ServiceProviderDashboardControler(DashboardController dashboardController, ServiceProvider user) throws SQLException {
        this.dashboard = dashboardController.getViewObject();
        bookingSlotsController = initBookingSlotsController(user);
        toggleButtonsController = initTogleButtonsController(user, dashboardController);
        this.user = user;
        serviceProviderSignUpForm = initServiceProviderSignUpFormController(user).getViewObject();
        serviceProviderBookingListController = initServiceProviderBookingListController(user);

        addDefaultMessageToDashboardOutput();
        dashboard.getSideBar().add(toggleButtonsController.getViewObject());

        addButtonsAListener();
        System.out.println(user);
    }

    public static ServiceProviderDashboardControler
    initServiceProviderDashboardController(DashboardController dashboardController, ServiceProvider user) throws SQLException {
        return new ServiceProviderDashboardControler(dashboardController, user);
    }

    private void addButtonsAListener(){
        addManageSlotsEntryAListener();
        addUpdateYourDetailsAListner();
        addViewBookingsSoFarAListener();
    }

    private void addManageSlotsEntryAListener() {
        toggleButtonsController.getViewObject().getManageSlotsEntry().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.getOutput().removeAll();
                dashboard.getOutput().add(bookingSlotsController.getViewObject());
                dashboard.getOutput().repaint();
                dashboard.getOutput().validate();

            }
        });
    }
    private void addUpdateYourDetailsAListner(){
        toggleButtonsController.getViewObject().getUpdateYourDetails().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.getOutput().removeAll();
                dashboard.getOutput().add(serviceProviderSignUpForm);
                dashboard.getOutput().repaint();
                dashboard.getOutput().validate();
            }
        });
    }

    private void addDefaultMessageToDashboardOutput(){
        dashboard.getOutput().removeAll();
        String allowance;
        if(user.getApprovedStatus().equalsIgnoreCase("approved")){
            allowance = new StringBuilder()
                    .append("Congratulations, you have been approved by the administrator <br>")
                    .append("Please, use the left side panel to browse options").toString();
        }else{
            allowance = new StringBuilder()
                    .append("You are still waiting on the approval of one of the administrators <br>")
                    .append("Please, be patient cause soon enough you will have the feedback").toString();
        }

        String txt = new StringBuilder().append("<html><body>")
                .append("<h1>").append(allowance).append("</body></html>").toString();
        JLabel msg = new JLabel(txt);
        dashboard.getOutput().add(msg);
    }

    private void addViewBookingsSoFarAListener(){
        toggleButtonsController.getViewObject()
                .getViewAllAppointmentsBookedSoFar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.getOutput().removeAll();
                dashboard.getOutput().add(serviceProviderBookingListController.getViewObject());
                dashboard.getOutput().repaint();
                dashboard.getOutput().validate();
            }
        });
    }


}
