package views.dashboard.customer;

import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJLabel;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ComplaintPanel extends MyCustomJPanel {

    private JTable table;
    private final MyCustomJPanel listOfBookingsPanel;
    private final MyCustomJPanel complaintContainer;
    private final MyCustomJButton submit;
    private final TextArea field;

    public ComplaintPanel() {
        super("Make a complaint about a booking you have experienced", 750, 700);
        submit = new MyCustomJButton("Submit", 100,40);
        field = new TextArea(5,1);
//        field.setPreferredSize(new Dimension(300,300));
        listOfBookingsPanel = new MyCustomJPanel("List Of Bookings", 300, 10);
        complaintContainer = new MyCustomJPanel("Complaint Manager", 300, 10);
        getContent().setLayout(new BorderLayout());
        getContent().add(listOfBookingsPanel, BorderLayout.LINE_START);

    }

    public void showComplaintContainer(){
        complaintContainer.getContent().setLayout(new GridLayout(5,1));
        complaintContainer.getContent().add(field);
        complaintContainer.getContent().add(submit.getButton());
        getContent().add(complaintContainer, BorderLayout.LINE_END);
        repaint();
        validate();
    }


    public void withListofBookingPanel(String[][] table, String[] strings) {
        this.table = new JTable(table, strings);
        JScrollPane jScrollPane = new JScrollPane(this.table);
        listOfBookingsPanel.add(jScrollPane);
        validate(); repaint();
    }

    public MyCustomJButton getSubmit() {
        return submit;
    }

    public TextArea getField() {
        return field;
    }

    public JTable getTable() {
        return table;
    }
}
