package views.dashboard.serviceProvider;

import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class SetBookingStatusView extends MyCustomJPanel {

    private JTable bookingsTable;
    private JComboBox<String> bookingStatesFilter;
    private MyCustomJPanel comboPanel;

    public SetBookingStatusView() {
        super("Set Booking Status", 700, 700);
        getContent().setLayout(new BorderLayout());

    }

    public JTable getBookingsTable() {
        return bookingsTable;
    }

    public JComboBox<String> getBookingStatesFilter() {
        return bookingStatesFilter;
    }

    public SetBookingStatusView withBookingStatesFilter(JComboBox bookingStatesFilterOptions){
        bookingStatesFilter = bookingStatesFilterOptions;
        comboPanel = new MyCustomJPanel("Filter by", 200,100);
        comboPanel.getContent().add(bookingStatesFilter);
        getContent().add(comboPanel, BorderLayout.NORTH);
        return this;
    }

    public SetBookingStatusView withBookingsTable(String[][] table, String[] colsName) {
        bookingsTable = new JTable(table, colsName);
        return this;
    }

    public MyCustomJPanel getComBoBoxFilterPanel() {

        return comboPanel;
    }

    public void withBookingsTable(JTable table) {
        getContent().removeAll();
        getContent().add(comboPanel, BorderLayout.NORTH);
        bookingsTable = table;
        getContent().add(table, BorderLayout.CENTER);
    }
}
