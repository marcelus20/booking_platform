package views.dashboard.serviceProvider;

import views.customComponents.MyCustomJPanel;
import javax.swing.*;
import java.awt.*;

/**
 * THIS PANEL IS FOR SERVICE TO SEE UPCOMMING BOOKINGS
 */
public class UpComingBookingsPanel extends MyCustomJPanel{

    /**
     * THE JTABLE CONTAINING THE UPCOMMING BOOKINGS
     */
    private final JTable upcomingBookingsTable;

    public UpComingBookingsPanel(JTable jTable) {
        super("Upcoming bookings - Click on the table row to change status of booking", 700,700);


        upcomingBookingsTable = jTable;
        JScrollPane jScrollPane = new JScrollPane(upcomingBookingsTable);
        upcomingBookingsTable.setPreferredScrollableViewportSize(new Dimension(650,400));
        getContent().add(jScrollPane);
    }

    /**
     * getter
     * @return
     */
    public JTable getUpcomingBookingsTable() {
        return upcomingBookingsTable;
    }

}
