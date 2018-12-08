package views.dashboard.customer;

import models.utils.ArrayListBuilder;
import views.ButtonPanelContainer;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJPanel;
import views.dashboard.Dashboard;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerDashboard extends Dashboard implements ButtonPanelContainer {

    private final MyCustomJButton searchServices;
    private final MyCustomJButton viewBookings;
    private final MyCustomJButton placeComplaint;
    private ConsoleSearch consoleSearch;
    private ConsoleManageBookings consoleManageBookings;
    private BookingPanel bookingPanel;
    private MyCustomJPanel complaintPanel;

    public CustomerDashboard() {
        super();
        searchServices = new MyCustomJButton("Search Barber or Hairdresser");
        viewBookings = new MyCustomJButton("View your bookings");
        placeComplaint = new MyCustomJButton("Place a complaint");
        consoleSearch = new ConsoleSearch();
        //consoleManageBookings = new ConsoleManageBookings(b);

        getSideBar().setLayout(new GridLayout(20,1));
        getButtonsPanel().forEach(b-> getSideBar().add(b.getButton()));
        //getOutput().add(consoleSearch);

    }



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

    @Override
    public List<MyCustomJButton> getButtonsPanel() {
        return new ArrayListBuilder<MyCustomJButton>()
                .add(searchServices).add(viewBookings).add(placeComplaint).build();
    }

    public MyCustomJPanel getComplaintPanel() {
        return complaintPanel;
    }

}
