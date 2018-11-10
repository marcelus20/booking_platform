package views.dashboard.serviceProviderView.bookingList;

import javax.swing.*;

public class ServiceProviderBookingList extends JPanel{

    private JLabel title;
    private JLabel instructions;
    private JPanel header;
    private JTable table;
    private JScrollPane scrollPane;

    public ServiceProviderBookingList() {
        title = new JLabel("List of Customer booked with you");
        instructions = new JLabel("Click on the table row to see details of the customer");
        header = new JPanel();
        table = new JTable();
        scrollPane = new JScrollPane();
    }

    public ServiceProviderBookingList(String title) {
        this.title = new JLabel(title);
        instructions = new JLabel("Click on the table row to see details of the customer");
        header = new JPanel();
        table = new JTable();
        scrollPane = new JScrollPane();
    }

    public JLabel getTitle() {
        return title;
    }

    public JLabel getInstructions() {
        return instructions;
    }

    public JPanel getHeader() {
        return header;
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setTable(String[][] data, String[] columnNames) {
        this.table = new JTable(data, columnNames);
    }

    public void setScrollPane(JTable table) {
        this.scrollPane = new JScrollPane(table);
    }
}
