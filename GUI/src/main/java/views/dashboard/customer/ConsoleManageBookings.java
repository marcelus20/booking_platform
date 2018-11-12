package views.dashboard.customer;

import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ConsoleManageBookings extends MyCustomJPanel {

    private JTable tableOfMyBookings;
    private JScrollPane jScrollPane;

    public ConsoleManageBookings(String[][] table, String[] colNames) {
        super("Manage your bookings", 700, 500);

        tableOfMyBookings = new JTable(table, colNames);

        if(table.length > 0){
            jScrollPane = new JScrollPane(tableOfMyBookings);
        }else{
            jScrollPane = new JScrollPane(new MyCustomJLabel("You have no bookings to manage at the moment.", 20).getLabel());
        }


        tableOfMyBookings.setPreferredScrollableViewportSize(new Dimension(680,500));
        getContent().add(jScrollPane);
    }

    public JTable getTableOfMyBookings() {
        return tableOfMyBookings;
    }
}
