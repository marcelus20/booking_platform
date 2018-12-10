package views.dashboard.customer;

import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;
import javax.swing.*;

/**
 * This class is to visually list all bookings that the logged user has booked.
 */
public class ConsoleManageBookings extends MyCustomJPanel { // it is a improved JPanel

    /**
     * List of attributes: a Jtable and scroll pane for the data
     */
    private JTable tableOfMyBookings;
    private JScrollPane jScrollPane;


    /**
     * Constructor: controller will be retrieving data from DB and populating this jTable with
     * data.
     * @param jTable
     */
    public ConsoleManageBookings(JTable jTable) {
        super("Manage your bookings", 700, 500);
        tableOfMyBookings = jTable;

        if(jTable.getRowCount() > 0){
            jScrollPane = new JScrollPane(tableOfMyBookings);
            getContent().add(jScrollPane);
        }else{
            jScrollPane = new JScrollPane(new MyCustomJLabel("You have no bookings to manage at the moment.", 20).getLabel());
        }
        validate(); repaint();
    }

    public JTable getTableOfMyBookings() {
        return tableOfMyBookings;
    }
}
