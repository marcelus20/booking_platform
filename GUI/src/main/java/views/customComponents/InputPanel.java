package views.customComponents;


import javax.swing.*;
import java.awt.*;


public class InputPanel extends JPanel {

    private final MyCustomJLabel label;
    private final MyCustomJField input;

    public InputPanel(final String label, final JTextField input) {
        this.label = new MyCustomJLabel(label);
        this.input = new MyCustomJField(input);

        this.input.getInput().setPreferredSize(new Dimension(200,40));
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        setLayout(new GridLayout(1,2));
        add(this.label.getLabel());add(this.input.getInput());
    }

    public JLabel getLabel() {
        return label.getLabel();
    }

    public MyCustomJField getInput() {
        return input;
    }

    private void validateAndRepaint(){
        validate();
        repaint();
    }

}
