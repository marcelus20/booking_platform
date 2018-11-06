package main;

import javax.swing.*;

public class SlotOptions extends JPanel {

    private JPanel sideBar;
    private JPanel footer;
    private JPanel output;
    private JPanel title;
    private JPanel addedDates;
    private JLabel addedDatesLabel;
    private JButton addDayToSchedule;
    private JButton save;

    public SlotOptions() {
        sideBar = new JPanel();
        footer = new JPanel();
        output = new JPanel();
        title = new JPanel();
        addedDates = new JPanel();
        addedDatesLabel = new JLabel("<html><body>Dates Added So far:");
        addDayToSchedule = new JButton("Add day to Schedule");
        save = new JButton("Save");
    }

    public JPanel getSideBar() {
        return sideBar;
    }

    public JPanel getFooter() {
        return footer;
    }

    public JPanel getOutput() {
        return output;
    }

    public JPanel getTitle() {
        return title;
    }

    public JButton getAddDayToSchedule() {
        return addDayToSchedule;
    }

    public JButton getSave() {
        return save;
    }

    public JPanel getAddedDates() {
        return addedDates;
    }

    public JLabel getAddedDatesLabel() {
        return addedDatesLabel;
    }

    public void setAddedDatesLabel(String addedDatesLabel) {
        String txt = new StringBuilder()
                .append(this.addedDatesLabel.getText())
                .append("<br>")
                .append(addedDatesLabel).toString();

        this.addedDatesLabel.setText(txt);
    }
}
