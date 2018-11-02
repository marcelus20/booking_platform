package controllers.controllers.signUpFormControllers;

import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import views.signUpForms.LocationForm;

import java.awt.*;

public class LocationFormController implements Controlls, ViewsObjectGetter<LocationForm> {

    private LocationForm locationForm;

    public LocationFormController() {
        locationForm = new LocationForm();
        config();
        setSizes();
        build();
    }
    public static LocationFormController initServiceProviderFormController(){
        return new LocationFormController();
    }

    @Override
    public void config() {

    }

    @Override
    public void build() {
        locationForm.add(locationForm.getFormName());
        locationForm.add(locationForm.getFirstLineAddress());
        locationForm.add(locationForm.getSecondLineAddress());
        locationForm.add(locationForm.getCity());
        locationForm.add(locationForm.getEirCode());
    }

    @Override
    public void setSizes() {
        locationForm.getFormName().setFont(new Font("Courier", Font.BOLD,30));
    }

    @Override
    public LocationForm getViewObject() {
        return locationForm;
    }


    /*
    private LocationForm locationForm;

    public LocationFormController() {
        locationForm = new LocationForm();
        config();
        setSizes();
        build();
    }

    public static LocationFormController initServiceProviderFormController(){
        return new LocationFormController();
    }

    @Override
    public void config() {
        locationForm.setLayout(new BoxLayout(locationForm, BoxLayout.Y_AXIS));
    }

    @Override
    public void build() {
        locationForm.add(locationForm.getFormName());
        locationForm.add(locationForm.getFirstLineAddress());
        locationForm.add(locationForm.getSecondLineAddress());
        locationForm.add(locationForm.getCity());
        locationForm.add(locationForm.getEirCode());

    }

    @Override
    public void setSizes() {

    }

    @Override
    public LocationForm getViewObject() {
        return locationForm;
    }
    */
}
