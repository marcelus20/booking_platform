package main;

import javax.swing.*;
import java.util.List;

public class Slots extends JPanel {
    private JLabel title;
    private JPanel buttonPanel;

    public Slots(JLabel title, JPanel buttonPanel) {
        this.title = title;
        this.buttonPanel = buttonPanel;
    }

    public Slots(String title) {
        this.title = new JLabel(title);
        buttonPanel = new JPanel();
    }

    public JLabel getTitle() {
        return title;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(List<JButton> buttonList) {
        buttonList.forEach(b->buttonPanel.add(b));
    }

}
