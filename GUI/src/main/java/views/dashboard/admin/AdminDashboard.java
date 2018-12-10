/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.admin;


import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.dashboard.Dashboard;
import java.awt.*;
import java.util.List;

/**
 * This class is the view Dashboard for admins.
 * This will be initially loaded with 4 buttons in the side bar dashboard.
 */
public class AdminDashboard extends Dashboard implements ButtonPanelContainer {

    /**
     * buttons to be displayed on the sidebar
     */
    private final MyCustomJButton viewActivities;
    private final MyCustomJButton verifyAServiceProvider;
    private final MyCustomJButton addAnotherAdminToSystem;
    private final MyCustomJButton complaintsHandler;
    /**
     * service provider configuration panel
     */
    private ServiceProviderVerification serviceProviderVerification;


    /**
     * CONSTRUCTOR, INITIALISING AND CONFIGURING ALL VIEW COMPONENTS
     */
    public AdminDashboard() {
        viewActivities = new MyCustomJButton("view activities of users", 100,50);
        verifyAServiceProvider = new MyCustomJButton("verify a service Provider", 100,50);
        addAnotherAdminToSystem = new MyCustomJButton("Add another admin");
        complaintsHandler = new MyCustomJButton("handle a complaint", 100, 50);
        serviceProviderVerification = new ServiceProviderVerification();
        getSideBar().getContent().setLayout(new GridLayout(10,1));
        getSideBar().getContent().add(viewActivities.getButton());
        getSideBar().getContent().add(verifyAServiceProvider.getButton());
        getSideBar().getContent().add(addAnotherAdminToSystem.getButton());
        getSideBar().getContent().add(complaintsHandler.getButton());
    }

    /**
     * Method from ButtonPanelContainer interface... returns the array of buttons for the controllers to loop through them
     * @return
     */
    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>().add(viewActivities)
                .add(verifyAServiceProvider).add(addAnotherAdminToSystem).add(complaintsHandler).build();
    }

    /**
     * the getter for the service provider Verification panel.
     * @return
     */
    public ServiceProviderVerification getServiceProviderVerification() {
        return serviceProviderVerification;
    }
}
