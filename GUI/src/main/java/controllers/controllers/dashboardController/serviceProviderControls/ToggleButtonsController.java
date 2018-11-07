package controllers.controllers.dashboardController.serviceProviderControls;

import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import controllers.controllers.dashboardController.DashboardController;
import models.entities.ServiceProvider;
import views.dashboard.serviceProviderView.toggleButtons.ToggleButtons;

import java.awt.*;


public class ToggleButtonsController
        implements Controlls<ToggleButtons>, ViewsObjectGetter<ToggleButtons> {

    private ToggleButtons toggleButtons;
    private ServiceProvider user;
    private DashboardController dashboardController;

    public ToggleButtonsController(ServiceProvider user, DashboardController dashboardController) {
        this.toggleButtons = new ToggleButtons();
        this.user = user;
        this.dashboardController = dashboardController;

        config();
        setSizes();
        build();
    }

    public static ToggleButtonsController
    initTogleButtonsController(ServiceProvider user, DashboardController dashboardController){
        return new ToggleButtonsController(user, dashboardController);
    }

    private void blockAllButtons(){
        toggleButtons.getManageSlotsEntry().setEnabled(false);
        toggleButtons.getUpdateYourDetails().setEnabled(false);
        toggleButtons.getViewAllAppointmentsBookedSoFar().setEnabled(false);
        toggleButtons.getViewAppointmentsBooked().setEnabled(false);
    }





    private Boolean userIsApproved(){
        return user.getApprovedStatus().equalsIgnoreCase("approved");
    }

    @Override
    public void config() {
        toggleButtons.setLayout(new GridLayout(0,1));
        if(!userIsApproved()){
            blockAllButtons();
        }
    }

    @Override
    public void build() {
        toggleButtons.add(toggleButtons.getManageSlotsEntry());
        toggleButtons.add(toggleButtons.getUpdateYourDetails());
        toggleButtons.add(toggleButtons.getViewAllAppointmentsBookedSoFar());
        toggleButtons.add(toggleButtons.getViewAppointmentsBooked());
    }

    @Override
    public void setSizes() {

    }

    @Override
    public ToggleButtons getViewObject() {
        return toggleButtons;
    }
}
