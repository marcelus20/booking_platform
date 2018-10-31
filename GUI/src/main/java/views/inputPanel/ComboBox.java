package views.inputPanel;


import javax.swing.*;

public class ComboBox  extends JPanel{
    private JLabel inputLabel;
    private JComboBox input;

    public ComboBox(final String label) {
        inputLabel = new JLabel(label);
        input = new JComboBox();
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(JLabel inputLabel) {
        this.inputLabel = inputLabel;
    }

    public JComboBox getInput() {
        return input;
    }

    public void setInput(JComboBox input) {
        this.input = input;
    }
}
