package views.dashboard.admin;


import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import views.dashboard.Dashboard;

import java.awt.*;
import java.util.List;

public class AdminDashboard extends Dashboard implements ButtonPanelContainer {

    private final MyCustomJButton viewActivities;
    private final MyCustomJButton verifyAServiceProvider;
    private final MyCustomJButton addAnotherAdminToSystem;
    private ServiceProviderVerification serviceProviderVerification;

    public AdminDashboard() {
        viewActivities = new MyCustomJButton("view activities of users", 100,50);
        verifyAServiceProvider = new MyCustomJButton("verify a service Provider", 100,50);
        addAnotherAdminToSystem = new MyCustomJButton("Add another admin");

        serviceProviderVerification = new ServiceProviderVerification();

        getSideBar().getContent().setLayout(new GridLayout(10,1));
        getSideBar().getContent().add(viewActivities.getButton());
        getSideBar().getContent().add(verifyAServiceProvider.getButton());
        getSideBar().getContent().add(addAnotherAdminToSystem.getButton());



    }

    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>().add(viewActivities)
                .add(verifyAServiceProvider).add(addAnotherAdminToSystem).build();
    }

    public ServiceProviderVerification getServiceProviderVerification() {
        return serviceProviderVerification;
    }
}
