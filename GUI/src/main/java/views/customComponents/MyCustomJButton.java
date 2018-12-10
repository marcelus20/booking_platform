package views.customComponents;

import javax.swing.JButton;
import java.awt.Dimension;

/**
 * This class is the custom JButotn. It will be born with (200,70) dimmension size by default but
 * its constructor also allows the object to be created with a custom size.
 */

public class MyCustomJButton{

    /**
     * JButton attribute
     */
    private final JButton button;

    /**
     * Full Contructorr
     * @param label
     * @param width
     * @param height
     */
    public MyCustomJButton(final String label, final Integer width, final Integer height) {
        button = new JButton(label);
        button.setPreferredSize(new Dimension(width, height));
    }

    /**
     * String label constructor
     * @param label
     */
    public MyCustomJButton(String label) {
        button = new JButton(label);
        button.setPreferredSize(new Dimension(200, 70)); // DEFAULT SIZE FOR MY BUTTONS
    }

    /**
     * gettter
     * @return
     */
    public JButton getButton() {
        return button;
    }

}
