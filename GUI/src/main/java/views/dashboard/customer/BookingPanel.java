package views.dashboard.customer;

import models.tuples.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;

public class BookingPanel extends MyCustomJPanel {

    private JTable table;
    private JScrollPane jScrollPane;

    public BookingPanel(ServiceProvider s) {
        super("Booking with "+ s.getCompanyFullName(), 600, 700);

        String[][] table = Tools.convertListOfBookingSlotsToArray(s.getBookingSlots());

        String[] ColNames = {"date and Time","service ID", "available"};

        this.table = new JTable(table, ColNames);
        if(s.getBookingSlots().size() > 0 ) {
            jScrollPane = new JScrollPane(this.table);
        }else{
            jScrollPane = new JScrollPane(new MyCustomJLabel(s.getCompanyFullName() + " Has no available slots").getLabel());
        }
        getContent().add(jScrollPane);

    }

    public JTable getTable() {
        return table;
    }
}
