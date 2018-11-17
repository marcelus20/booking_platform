package views.dashboard.admin;


import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ServiceProviderVerification extends MyCustomJPanel {

    private JTable tableOfServices;

    public ServiceProviderVerification() {
        super("List of Service Provider pendents waiting on approval/rejection", 700, 600);

    }

    public ServiceProviderVerification withTableOfServices(JTable tableOfServices) {
        this.tableOfServices = tableOfServices;
        validate(); repaint();
        return this;
    }

    public JTable getTableOfServices() {
        return tableOfServices;
    }
}
