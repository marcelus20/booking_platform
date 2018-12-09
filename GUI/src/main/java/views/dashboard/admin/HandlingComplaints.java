package views.dashboard.admin;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;

public class HandlingComplaints extends MyCustomJPanel {

    private JTable complaintTable;

    public HandlingComplaints(JTable complaintTable) {
        super("Complaints Handler Panel - click on the line to change complaint status", 500,500);
        this.complaintTable = complaintTable;
        JScrollPane scrollPane = new JScrollPane(this.complaintTable);
        getContent().add(scrollPane);
    }


}
