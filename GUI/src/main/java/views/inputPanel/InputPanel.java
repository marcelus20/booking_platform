package views.inputPanel;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    private JLabel inputLabel;
    private JTextField input;

    public InputPanel(final String label) {
        inputLabel = new JLabel(label);
        input = new JTextField();
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(JLabel inputLabel) {
        this.inputLabel = inputLabel;
    }

    public JTextField getInput() {
        return input;
    }

    public void setInput(JTextField input) {
        this.input = input;
    }
}
