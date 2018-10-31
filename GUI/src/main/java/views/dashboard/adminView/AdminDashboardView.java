package views.dashboard.adminView;

import javax.swing.*;

public class AdminDashboardView extends JPanel {

    private final JButton toggleListOfCustomers;

    private final JButton toggleListOfServiceProviders;

    private final JButton refreash;


    public AdminDashboardView() {
        toggleListOfCustomers = new JButton("Toggle list of Customers");
        toggleListOfServiceProviders = new JButton("Togle list of Service Providers");
        refreash = new JButton("Refreash");

    }

    public JButton getToggleListOfCustomers() {
        return toggleListOfCustomers;
    }

    public JButton getToggleListOfServiceProviders() {
        return toggleListOfServiceProviders;
    }

    public JButton getRefreash() {
        return refreash;
    }
}
