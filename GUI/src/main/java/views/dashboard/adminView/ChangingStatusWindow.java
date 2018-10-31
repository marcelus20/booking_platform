package views.dashboard.adminView;

import javax.swing.*;
import java.awt.*;

public class ChangingStatusWindow extends JFrame {

    private JPanel container;
    private JPanel serviceProviderModel;
    private JButton approve;
    private JButton disapprove;

    public ChangingStatusWindow() throws HeadlessException {
        container = new JPanel();
        serviceProviderModel = new JPanel();

    }

    public JPanel getContainer() {
        return container;
    }

    public JPanel getServiceProviderModel() {
        return serviceProviderModel;
    }

    public void setServiceProviderModel(JPanel serviceProviderModel) {
        this.serviceProviderModel = serviceProviderModel;
    }

    public JButton getApprove() {
        return approve;
    }

    public void setApprove(JButton approve) {
        this.approve = approve;
    }

    public JButton getDisapprove() {
        return disapprove;
    }

    public void setDisapprove(JButton disapprove) {
        this.disapprove = disapprove;
    }
}
