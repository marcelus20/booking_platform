package views.customComponents;


import javax.swing.*;
import java.awt.*;

/**
 * This class wraps an TextField along with its label in a panel. Making it
 * Well arranged/sorted and configured.
 *
 * Typically used in forms, when you have, for example the label email with the input right after
 *
 *  _______________________________________
 * |               |______________________|
 * |Email:         |__some@somewhere.com _|
 * |_______________|______________________|
 */
public class InputPanel extends JPanel {

    /**
     * attributes
     */
    private final MyCustomJLabel label;
    private final MyCustomJField input;

    /**
     * Constructor - it takes a String label and a Jtextfield
     * @param label
     * @param input
     */
    public InputPanel(final String label, final JTextField input) {
        this.label = new MyCustomJLabel(label);
        this.input = new MyCustomJField(input);

        this.input.getInput().setPreferredSize(new Dimension(200,40));
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        setLayout(new GridLayout(1,2));
        add(this.label.getLabel());add(this.input.getInput());
    }

    /**
     * getters
     * @return
     */
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
