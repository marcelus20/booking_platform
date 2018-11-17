package views.dashboard.serviceProvider;

import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class UpComingBookingsPanel extends MyCustomJPanel{

    private final JTable upcomingBookingsTable;

    public UpComingBookingsPanel(JTable jTable) {
        super("Upcoming bookings - Click on the table row to cancel a booking", 700,700);

        upcomingBookingsTable = jTable;
        JScrollPane jScrollPane = new JScrollPane(upcomingBookingsTable);
        upcomingBookingsTable.setPreferredScrollableViewportSize(new Dimension(650,400));
        getContent().add(jScrollPane);
    }

    public JTable getUpcomingBookingsTable() {
        return upcomingBookingsTable;
    }

}
