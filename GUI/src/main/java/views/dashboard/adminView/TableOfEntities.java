package views.dashboard.adminView;

import javax.swing.*;

public  abstract class TableOfEntities extends JPanel {

    protected JLabel title;
    protected JTable table;
    protected JScrollPane scrollPane;

    public TableOfEntities() {
    }

    public JLabel getTitle() {
        return title;
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
}
