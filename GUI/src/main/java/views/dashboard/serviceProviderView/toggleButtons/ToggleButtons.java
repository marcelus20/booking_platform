package views.dashboard.serviceProviderView.toggleButtons;

import javax.swing.*;

public class ToggleButtons extends JPanel{

    private JButton manageSlotsEntry;
    private JButton viewAppointmentsBooked;
    private JButton viewAllAppointmentsBookedSoFar;
    private JButton updateYourDetails;

    public ToggleButtons() {
        manageSlotsEntry = new JButton("Insert available slots");
        viewAppointmentsBooked = new JButton("View Apoointments comming up");
        viewAllAppointmentsBookedSoFar = new JButton("View All Bookings so far");
        updateYourDetails = new JButton("Update your details");
    }

    public JButton getManageSlotsEntry() {
        return manageSlotsEntry;
    }

    public JButton getViewAppointmentsBooked() {
        return viewAppointmentsBooked;
    }

    public JButton getViewAllAppointmentsBookedSoFar() {
        return viewAllAppointmentsBookedSoFar;
    }

    public JButton getUpdateYourDetails() {
        return updateYourDetails;
    }
}
