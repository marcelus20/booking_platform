package views.dashboard.serviceProvider;

import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.dashboard.Dashboard;

import java.util.List;

public class ServiceDashboard extends Dashboard implements ButtonPanelContainer {

    private final MyCustomJButton viewUpcommingBookings;
    private final MyCustomJButton setAvailability;
    private final MyCustomJButton setStatusOfApppointment;
    private UpComingBookingsPanel upComingBookingsPanel;
    private SlotManagementPanel slotManagementPanel;

    public ServiceDashboard() {
        viewUpcommingBookings = new MyCustomJButton("View Upcoming bookings");
        setAvailability = new MyCustomJButton("Set slots to available or not");
        setStatusOfApppointment = new MyCustomJButton("Set a booking to complete");

    }

    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>().add(viewUpcommingBookings)
                .add(setAvailability).add(setStatusOfApppointment).build();
    }

    public ServiceDashboard withUpcomingBookingsPanel(String[][] table, String[] colsNames){
        this.upComingBookingsPanel = new UpComingBookingsPanel(table, colsNames);
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
}
