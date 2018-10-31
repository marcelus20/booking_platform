package views.inputPanel;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public abstract class InputPanel <I extends JTextComponent> extends JPanel {

    private JLabel inputLabel;
    protected I input;

    public InputPanel(final String label) {
        inputLabel = new JLabel(label);
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(JLabel inputLabel) {
        this.inputLabel = inputLabel;
    }

    public I getInput() {
        return input;
    }

    public void setInput(I input) {
        this.input = input;
    }
}
