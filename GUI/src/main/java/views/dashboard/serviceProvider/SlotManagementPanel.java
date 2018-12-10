package views.dashboard.serviceProvider;

import com.github.lgooddatepicker.components.CalendarPanel;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import java.awt.*;

/**
 * THIS PANEL WILL TOGGLE WHEN SERVICEPROVIDER WANTS TO ADD SLOTS TO THE SYSTEM.
 */

public class SlotManagementPanel extends MyCustomJPanel { // ITS A PANEL


    /**
     * list of attributes.
     */
    private final MyCustomJPanel calendarPanelWrapper;
    private final CalendarPanel calendarPanel;
    private final MyCustomJPanel slotOutputPanel; // RIGHT SIDE PANEL
    private final MyCustomJPanel notAvailableSlots;//LEFT SIDE PANEL
    private final MyCustomJPanel availableSlots;
    private final MyCustomJButton saveButton;

    /**
     * CONSTRUCTOR
     */
    public SlotManagementPanel() {
        super("Slots Panel management", 700, 800);
        this.calendarPanel = new CalendarPanel();
        saveButton = new MyCustomJButton("Save", 80,30);
        calendarPanelWrapper = new MyCustomJPanel("Pick a date", 300,300);
        calendarPanelWrapper.getContent().add(calendarPanel);
        slotOutputPanel = new MyCustomJPanel("Slots output Panel", 700, 300);
        notAvailableSlots = new MyCustomJPanel("click on the slots to set to available", 300,280);
        availableSlots = new MyCustomJPanel("Available slots", 300,280);
        mount();
    }

    /**
     * ADDS its attributes panels the content panel from super class
     */
    private void mount(){
        getContent().setLayout(new GridLayout(2,1));
        getContent().add(calendarPanelWrapper);
        slotOutputPanel.getContent().add(notAvailableSlots);
        slotOutputPanel.getContent().add(availableSlots);
        notAvailableSlots.getContent().setLayout(new GridLayout(6,4));
        availableSlots.getContent().setLayout(new GridLayout(6,4));
        slotOutputPanel.getContent().add(saveButton.getButton());

    }

    /**
     * GETTERS
     * @return
     */
    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public MyCustomJPanel getSlotOutputPanel() {
        return slotOutputPanel;
    }

    public MyCustomJPanel getCalendarPanelWrapper() {
        return calendarPanelWrapper;
    }

    public MyCustomJPanel getNotAvailableSlots() {
        return notAvailableSlots;
    }

    public MyCustomJPanel getAvailableSlots() {
        return availableSlots;
    }

    public MyCustomJButton getSaveButton() {
        return saveButton;
    }
}
