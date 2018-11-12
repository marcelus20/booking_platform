package views.dashboard.serviceProvider;

import views.customComponents.MyCustomJPanel;

import javax.swing.*;

public class UpComingBookingsPanel extends MyCustomJPanel{

    private final JTable upcomingBookingsTable;

    public UpComingBookingsPanel(String[][] t, String[]colNames) {
        super("Upcoming bookings - Click on the table row to cancel a booking", 700,700);

        upcomingBookingsTable = new JTable(t, colNames);
        JScrollPane jScrollPane = new JScrollPane(upcomingBookingsTable);
        getContent().add(jScrollPane);
    }

    public JTable getUpcomingBookingsTable() {
        return upcomingBookingsTable;
    }

}
