/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.customer;

import models.tuples.entitiesRepresentation.BookingSlots;
import models.tuples.joinedEntities.ServiceProviderTableView;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is to display the bookings that a Customer has bookeed so far.
 * It is toggled by the sidebar Option in Customer dashboard class
 */
public class BookingPanel extends MyCustomJPanel {

    /**
     * List of attributes
     */
    private JTable table;
    private JScrollPane jScrollPane;

    /**
     * Constructor - to be called by a controller
     * @param bookingSlots
     * @param serviceProviderTableView
     */
    public BookingPanel(List<BookingSlots> bookingSlots, ServiceProviderTableView serviceProviderTableView) {
        super("Booking with "+ serviceProviderTableView.getServiceName(), 600, 700);

        /**
         * Turning the list of slots into a 2D array by using stram().map()
         */
        String[][] table = bookingSlots.stream().map(slot->Arrays.asList(
                slot.getTimestamp()+"", slot.getServiceId(), slot.getAvailability()+""
        ).toArray(new String[3]))
                .collect(Collectors.toList())
                .toArray(new String[bookingSlots.size()][]);
        // setting up columns
        String[] ColNames = {"date and Time","service ID", "available"};

        /**
         * initialising the Jtable attribute and the jScrollPane
         */
        this.table = new JTable(table, ColNames);
        if(bookingSlots.size() > 0 ) {
            jScrollPane = new JScrollPane(this.table);
        }else{
            jScrollPane = new JScrollPane(new MyCustomJLabel(serviceProviderTableView.getServiceName() + " Has no available slots").getLabel());
        }
        /**
         * adding scrol pane to getContent panel (from this class that extends MyCustomJPanel)
         */
        getContent().add(jScrollPane);
    }

    /**
     * getter
     * @return
     */
    public JTable getTable() {
        return table;
    }
}
