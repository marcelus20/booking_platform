/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.customer;

import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import views.dashboard.Dashboard;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This is the customer Dashboard panel.
 * This will initially contain 3 buttons in the sideBar for the customer to browse through the
 * options search for barbers, see bookings and adding a complaint.
 */
public class CustomerDashboard extends Dashboard implements ButtonPanelContainer {

    /**
     * List of attributes, buttons and panels
     */
    private final MyCustomJButton searchServices;
    private final MyCustomJButton viewBookings;
    private final MyCustomJButton placeComplaint;
    private ConsoleSearch consoleSearch;
    private ConsoleManageBookings consoleManageBookings;
    private BookingPanel bookingPanel;
    private MyCustomJPanel complaintPanel;

    /**
     * CONSTRUCTOR
     */
    public CustomerDashboard() {
        super();
        searchServices = new MyCustomJButton("Search Barber or Hairdresser");
        viewBookings = new MyCustomJButton("View your bookings");
        placeComplaint = new MyCustomJButton("Place a complaint");
        consoleSearch = new ConsoleSearch();
        getSideBar().setLayout(new GridLayout(20,1));
        getButtonsPanel().forEach(b-> getSideBar().add(b.getButton()));
    }

    /**
     * getters
     * @return
     */

    public JButton getSearchServices() {
        return searchServices.getButton();
    }

    public JButton getViewBookings() {
        return viewBookings.getButton();
    }

    public JButton getPlaceComplaint() {
        return viewBookings.getButton();
    }

    public ConsoleSearch getConsoleSearch() {
        return consoleSearch;
    }

    public BookingPanel getBookingPanel() {
        return bookingPanel;
    }

    /**
     * SETTERS
     * @param consoleSearch
     */
    public void setConsoleSearch(ConsoleSearch consoleSearch) {
        this.consoleSearch = consoleSearch;
    }

    public void setBookingPanel(BookingPanel bp){
        bookingPanel = bp;
    }

    public void setComplaintPanel(MyCustomJPanel complaintPanel) {
        this.complaintPanel = complaintPanel;
    }

    public void withOutput(MyCustomJPanel p){
        getOutput().removeAll();
        getOutput().add(p);
        validadeAndRepaint();
    }



    public void renewConsoleSearch(){
        consoleSearch = new ConsoleSearch();
    }

    /**
     * IMPLEMENTS ButtonPanelContainer, therefore, this override method will return the array of buttons this
     * dashboard have in order for the controllers to give them a listener.
     * @return
     */
    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>()
                .add(searchServices).add(viewBookings).add(placeComplaint).build();
    }

    public MyCustomJPanel getComplaintPanel() {
        return complaintPanel;
    }

}
