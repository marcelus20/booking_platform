package views.dashboard.admin;


import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ServiceProviderVerification extends MyCustomJPanel {

    private JTable tableOfServices;

    public ServiceProviderVerification() {
        super("List of Service Provider pendents waiting on approval/rejection", 700, 700);

    }

    public ServiceProviderVerification withTableOfServices(JTable tableOfServices) {
        getContent().setLayout(new BorderLayout());
        getContent().removeAll();
        this.tableOfServices = tableOfServices;
        getContent().add(new JScrollPane(this.tableOfServices), BorderLayout.CENTER);
        getContent().validate(); getContent().repaint();
        validate(); repaint();
        return this;
    }

    public JTable getTableOfServices() {
        return tableOfServices;
    }
}
