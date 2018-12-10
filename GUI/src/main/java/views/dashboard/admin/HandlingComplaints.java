/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package views.dashboard.admin;
import views.customComponents.MyCustomJPanel;

import javax.swing.*;

/**
 * The handling complaints Panel.
 * this panel will be toggled when admin clicks on the handle Complaints button in dashboard sidebar panel.
 */
public class HandlingComplaints extends MyCustomJPanel {

    /**
     * contains a table of complaints
     */
    private JTable complaintTable;

    /**
     * Constructor:
     * the table of complaints come from outside, controller will be passing a JTable with data from db
     * @param complaintTable
     */
    public HandlingComplaints(JTable complaintTable) {
        super("Complaints Handler Panel - click on the line to change complaint status", 500,500);
        this.complaintTable = complaintTable;
        JScrollPane scrollPane = new JScrollPane(this.complaintTable);
        getContent().add(scrollPane);
    }


}
