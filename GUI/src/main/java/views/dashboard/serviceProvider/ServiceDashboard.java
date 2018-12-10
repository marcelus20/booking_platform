package views.dashboard.serviceProvider;

import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.dashboard.Dashboard;
import javax.swing.*;
import java.util.List;

/**
 * This is the dashboard for the service providers users.
 * it will initially have 3 buttons on the sideBar with the options for the
 * services to browse through the application.
 */
public class ServiceDashboard extends Dashboard implements ButtonPanelContainer {

    /**
     * List of attributes, 3 buttons and 3 panels that will be alternated by the user
     */
    private final MyCustomJButton viewUpcommingBookings;
    private final MyCustomJButton setAvailability;
    private final MyCustomJButton setStatusOfApppointment;
    private UpComingBookingsPanel upComingBookingsPanel;
    private SlotManagementPanel slotManagementPanel;
    private SetBookingStatusView setBookingStatusView;

    /**
     * CONSTRUCTOR INITIALISES JUST BUTTONS. Panels will initialise as user clicks on the buttons
     */
    public ServiceDashboard() {
        viewUpcommingBookings = new MyCustomJButton("View Upcoming bookings");
        setAvailability = new MyCustomJButton("Set slots to available or not");
        setStatusOfApppointment = new MyCustomJButton("Set a booking to complete");

    }

    /**
     * returns an array with all Dashboard buttons for the controllers to assign a listener
     * @return
     */
    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>().add(viewUpcommingBookings)
                .add(setAvailability).add(setStatusOfApppointment).build();
    }

    /**
     * setters and getters
     * @param jTable
     * @return
     */
    public ServiceDashboard withUpcomingBookingsPanel(JTable jTable){
        this.upComingBookingsPanel = new UpComingBookingsPanel(jTable);
        return this;
    }

    public SlotManagementPanel getSlotManagementPanel() {
        return slotManagementPanel;
    }

    public UpComingBookingsPanel getUpComingBookingsPanel() {
        return upComingBookingsPanel;
    }

    public ServiceDashboard withSlotsManagementPanel(SlotManagementPanel slotManagementPanel) {
        this.slotManagementPanel = slotManagementPanel;
        return this;
    }

    public SetBookingStatusView getSetBookingStatusView() {
        return setBookingStatusView;
    }

    public ServiceDashboard withSetBookingStatusView(SetBookingStatusView setBookingStatusView) {
        this.setBookingStatusView = setBookingStatusView;
        return this;
    }

}
