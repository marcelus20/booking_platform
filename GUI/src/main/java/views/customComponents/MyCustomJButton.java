package views.customComponents;

import javax.swing.JButton;
import java.awt.Dimension;

/**
 * This class is the custom JButotn. It will be born with (200,70) dimmension size by default but
 * its constructor also allows the object to be created with a custom size.
 */

public class MyCustomJButton{

    private final JButton button;

    public MyCustomJButton(final String label, final Integer width, final Integer height) {
        button = new JButton(label);
        button.setPreferredSize(new Dimension(width, height));
    }

    public MyCustomJButton(String label) {
        button = new JButton(label);
        button.setPreferredSize(new Dimension(200, 70)); // DEFAULT SIZE FOR MY BUTTONS
    }

    public JButton getButton() {
        return button;
    }

}
