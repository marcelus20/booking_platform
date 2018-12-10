package views.dashboard.admin;


import views.customComponents.MyCustomJPanel;

import javax.swing.*;

/**
 * Here is the Panel for the service provider verification view.
 * Admins can set Service provider status in this panel and will be toggled when
 * pressed the button on the dashboard sidebar
 */
public class ServiceProviderVerification extends MyCustomJPanel {

    /**
     * Jtable with service providers
     */
    private JTable tableOfServices;

    /**
     * constructor will initilise just the superclass, not the Jtable
     */
    public ServiceProviderVerification() {
        super("List of Service Provider pendents waiting on approval/rejection", 700, 600);
    }

    /**
     * controllers will set the JTable by using this method after the the data from DB has been retrieved
     * @param tableOfServices
     * @return
     */
    public ServiceProviderVerification withTableOfServices(JTable tableOfServices) {
        this.tableOfServices = tableOfServices;
        validate(); repaint();
        return this;
    }

    /**
     * getter
     * @return
     */
    public JTable getTableOfServices() {
        return tableOfServices;
    }
}
